package net.megx.ws.genomes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.jcr.RepositoryException;

import net.megx.storage.StorageException;
import net.megx.storage.StoredResource;
import net.megx.ws.genomes.resources.WorkspaceAccess;
/*
  ACTG
  
  AAA ->K       CAA ->Q       TAA ->.       GAA ->E
  AAC ->N       CAC ->H       TAC ->T       GAC ->D
  AAT ->N       CAT ->H       TAT ->T       GAT ->D
  AAG ->K       CAG ->Q       TAG ->.       GAG ->E
  
  ACA ->T       CCA ->P       TCA ->S       GCA ->A
  ACC ->T       CCC ->P       TCC ->S       GCC ->A
  ACT ->T       CCT ->P       TCT ->S       GCT ->A
  ACG ->T       CCG ->P       TCG ->S       GCG ->A
  
  ATA ->I       CTA ->L       TTA ->L       GTA ->V
  ATC ->I       CTC ->L       TTC ->F       GTC ->V
  ATT ->I       CTT ->L       TTT ->F       GTT ->V
  ATG ->M       CTG ->L       TTG ->L       GTG ->V
  
  AGA ->R       CGA ->R       TGA ->.       GGA ->G
  AGC ->S       CGC ->R       TGC ->C       GGC ->G
  AGT ->S       CGT ->R       TGT ->C       GGT ->G
  AGG ->R       CGG ->R       TGG ->W       GGG ->G
  
  a-0
  c-1
  t-2
  g-3

 */
public class SixFrameTranslation extends BaseGenomeService{

	private static final char [] AMINO_ACIDS = new char [] {
		'K','N','N','K',
		'T','T','T','T',
		'I','I','I','M',
		'R','S','S','R',
		
		'Q','H','H','Q',
		'P','P','P','P',
		'L','L','L','L',
		'R','R','R','R',
		
		'*','T','T','*',
		'S','S','S','S',
		'L','F','F','L',
		'.','C','C','W',
		
		'E','D','D','E',
		'A','A','A','A',
		'V','V','V','V',
		'G','G','G','G',
		'-'
	};
	
	
	public SixFrameTranslation(WorkspaceAccess access) {
		super(access);
	}
	
	/**
	 * 
	 * @param chunk
	 * @param chunkOut
	 * @return
	 * @author Mile Stefanovski
	 */
	public int translate(byte [] chunk, int start, int size, byte [] chunkOut){
		int bl = (size-start)/3;
		bl*=3;
		int j = 0;
		for(int i = start; i < bl; i+=3,j++){
			chunkOut[j] = (byte)AMINO_ACIDS[((chunk[i]&6)<<3)|((chunk[i+1]&6)<<1) | ((chunk[i+2]>>1)&3)];
		}
		if(bl < size){
			chunkOut[j] = (byte)AMINO_ACIDS[AMINO_ACIDS.length-1];
			j++;
		}
		return j;
	}
	
	public int translateReverseFrame(byte [] chunk, int start, int size, byte [] chunkOut){
		int bl = (size-start)/3;
		bl*=3;
		int j = 0;
		for(int i = size-1-start; i >(size-bl); i-=3,j++){
			chunkOut[j] = (byte)AMINO_ACIDS[((chunk[i]&6)<<3)|((chunk[i-1]&6)<<1) | ((chunk[i-2]>>1)&3)];
		}
		if(bl < size){
			chunkOut[j] = (byte)AMINO_ACIDS[AMINO_ACIDS.length-1];
			j++;
		}
		return j;
	}
	
	
	public int translateOnFrame(int frame, byte [] input, byte [] out) throws IOException{
		if(frame > 0){
			return translate(input, frame-1, input.length-frame-1, out);
		}else{
			frame = -frame-1;
			return translateReverseFrame(input, frame, input.length-frame, out);
		}
	}
	
	
	public void sixFrameTranslate(String user, int [] frames, String inputFile, 
			OutputStream output, boolean includeOriginalFASTA, int lineSize) throws RepositoryException, 
		URISyntaxException, StorageException, IllegalArgumentException, IOException{
		for(int frame: frames){
			if(frame < -3 || frame == 0 || frame > 3)
				throw new IllegalArgumentException("Invalid frame: " + frame);
		}
		
		StoredResource input = getAccess().getResourceJCR(user, inputFile);
		String fastaHeader = null;
		StringBuffer sequence = new StringBuffer();
		String line = null;
		
		InputStream srStream = input.read();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(srStream));
		while((line = reader.readLine())!= null){
			line = line.trim();
			if(line.startsWith(">")){
				fastaHeader = line;
				if(sequence.length() > 0){
					// call translate function here
					doTranslate(frames, fastaHeader, sequence.toString().getBytes(), 
							output, includeOriginalFASTA, lineSize);
					sequence = new StringBuffer();
					
				}
			}else if(!"".equals(line)){
				sequence.append(line);
			}
		}
		if(sequence.length() > 0 && fastaHeader != null){
			doTranslate(frames, fastaHeader, sequence.toString().getBytes(),
					output, includeOriginalFASTA, lineSize);
		}
		
		srStream.close();
	}
	
	public void sixFrameTranslate(String user, int [] frames, String inputFile, 
			String outputFile, boolean includeOriginalFASTA, int lineSize) throws RepositoryException, 
			URISyntaxException, StorageException, IllegalArgumentException, IOException{
		StoredResource outFile = getAccess().createNewResourceJCR(user, outputFile);
		OutputStream out = outFile.write();
		sixFrameTranslate(user, frames, inputFile, out, includeOriginalFASTA, lineSize);
		out.close();
	}
	
	
	
	
	protected static final String NEW_LINE = String.format("%n");
	
	protected void doTranslate(int[] frames, String fastaHeader, byte[] bytes,
			OutputStream output, boolean includeOriginalFASTA, int lineSize) throws IOException {
		if(includeOriginalFASTA){
			writeFASTA(fastaHeader, bytes, bytes.length, lineSize, output);
		}
		byte [] outBuffer = new byte[bytes.length/3 +1];
		
		
		for(int frame: frames){
			int size = translateOnFrame(frame, bytes, outBuffer);
			writeFASTA(getFASTAHeader(fastaHeader, frame), outBuffer, size, lineSize, output);
		}
	}

	protected String getFASTAHeader(String originalHeader, int frame){
		return String.format("%s|frame=%d", originalHeader, frame);
	}
	
	
	protected void writeFASTA(String fastaHeader, byte[] bytes, int writeSize, int lineSize, OutputStream out) throws IOException {
		out.write(fastaHeader.getBytes());
		out.write(NEW_LINE.getBytes());
		lineSize = lineSize > 0 ? lineSize : writeSize;
		for(int i = 0; i < bytes.length; i+=lineSize){
			out.write(bytes, i, (i+lineSize) < bytes.length ? lineSize : bytes.length-i-1);
			out.write(NEW_LINE.getBytes());
		}
		out.write(NEW_LINE.getBytes());
		out.flush();
	}
}
