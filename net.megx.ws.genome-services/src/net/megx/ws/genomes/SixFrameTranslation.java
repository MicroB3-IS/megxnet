package net.megx.ws.genomes;

import java.util.Random;

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
		
		'.','T','T','.',
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

	
	public int translate(byte [] chunk, byte [] chunkOut){
		int l = chunk.length/3;
		int c = 0;
		char curr = 0;
		for(int i = 0; i < l; i++){
			boolean unableToTranslate = false;
			int translated = 0 ;
			for(int j = 0, p=16; j < 3; j++,p/=4){
				curr = (char)chunk[i*3+j];
				switch(curr){
				case 'a':
				case 'A':
					break;
				case 'c':
				case 'C':
					translated+=p;
					break;
				case 't':
				case 'T':
					translated+=p*2;
					break;
				case 'g':
				case 'G':
					translated+=p*3;
					break;
				default:
					unableToTranslate = true;
					break;
				}
			}
			if(unableToTranslate)
				translated = AMINO_ACIDS.length-1;
			chunkOut[i] = (byte)AMINO_ACIDS[translated];
			c++;
		}
		if(l*3 < chunk.length){
			chunkOut[c] = (byte)AMINO_ACIDS[AMINO_ACIDS.length-1];
			c++;
		}
		return c;
	}
	
	/**
	 * 
	 * @param chunk
	 * @param chunkOut
	 * @return
	 * @author Mile Stefanovski
	 */
	public int translate2(byte [] chunk, byte [] chunkOut){
		int bl = chunk.length/3;
		bl*=3;
		int j = 0;
		for(int i = 0; i < bl; i+=3,j++){
			chunkOut[j] = (byte)AMINO_ACIDS[((chunk[i]&6)<<3)|((chunk[i+1]&6)<<1) | ((chunk[i+2]>>1)&3)];
		}
		if(bl < chunk.length){
			chunkOut[j] = (byte)AMINO_ACIDS[AMINO_ACIDS.length-1];
			j++;
		}
		return j;
	}
	
	public static void main(String[] args) {
		SixFrameTranslation sft = new SixFrameTranslation(null);
		int IN_SIZE = 384*1024;
		int OUT_SIZE = IN_SIZE/3;
		byte [] input = new byte [IN_SIZE]; // 12KiB
		byte [] output = new byte[OUT_SIZE];
		char [] NUC = {'A','C','T','G'};
		
		
		Random r = new Random();
		for(int i = 0; i < IN_SIZE; i++){
			input[i] = (byte)NUC[Math.abs(r.nextInt())%4];
		}
		
		int ITERATIONS = 1000;
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < ITERATIONS; i++){
			sft.translate2(input, output);
		}
		long end = System.currentTimeMillis();
		
		long diff = end-start;
		double est = IN_SIZE*ITERATIONS/((double)diff);
		System.out.println("-------");
		System.out.println(Integer.MAX_VALUE);
		System.out.println(String.format("%x",Long.MAX_VALUE));
		System.out.println("-------");
		System.out.println(est + "Bps");
		long TOTAL_SIZE = 1024L*1024L*1024L*1024L;
		double totalSecs = TOTAL_SIZE/est;
		System.out.println(totalSecs);
		System.out.println((totalSecs/86400));
	}
	
}
