package net.megx.ws.genomes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;

import net.megx.storage.StoredResource;
import net.megx.ws.genomes.resources.WorkspaceAccess;

public class DiNucOddsRatio extends BaseGenomeService {

	private static final String CSV_HEADER = "SITE	pAA/pTT	pAC/pGT	pCC/pGG	pCA/pTG	pGA/pTC	pAG/pCT	pAT	pCG	pGC	pTA";
	private static final String CSV_ROW = "%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s";

	public DiNucOddsRatio(WorkspaceAccess access) {
		super(access);
	}

	/**
	 * Calculates the di-nucleotide odds ratio for each FASTA entry in the
	 * specified input file and writes the results in the output file. The
	 * output is in CSV format.
	 * 
	 * @param username
	 *            Usually the login name of the user. This is needed to access
	 *            the resource in the storage.
	 * @param fastaResource
	 *            URI relative to the user's workspace root to the FASTA file
	 *            that will be processed
	 * @param csvOut
	 *            Relative URI to the result CSV file. New file will be created
	 *            for the processing. In case the file cannot be created or
	 *            already exist an error is thrown
	 * @throws IOException
	 *             in case the {@link InputStream} could not be read from or
	 *             {@link OutputStream} could not be written to
	 * @throws Exception
	 *             when an exception occurs in the processing of the FASTA
	 *             entries
	 */
	public void calculateDiNucleotideOddsRatio(String username,
			String fastaResource, OutputStream csvOut) throws IOException,
			Exception {

		StoredResource resource = getAccess().getResourceJCR(username,
				fastaResource);
		InputStream readstream = resource.read();

		calculateDiNucleotideOddsRatio(readstream, csvOut);

		readstream.close();

	}

	/**
	 * Calculates the di-nucleotide odds ratio for each FASTA entry from the
	 * input and writes the result in CSV format in the output stream. The FASTA
	 * resource is looked up by the supplied name in the accessible workspace
	 * for the given user.
	 * 
	 * @param username
	 *            Usually the login name of the user. This is needed to access
	 *            the resource in the storage.
	 * @param fastaResource
	 *            URI relative to the user's workspace root to the FASTA file
	 *            that will be processed
	 * @param csvOut
	 *            {@link OutputStream} in which the results in form of CSV will
	 *            be printed
	 * @throws IOException
	 *             in case the {@link InputStream} could not be read from or
	 *             {@link OutputStream} could not be written to
	 * @throws Exception
	 *             when an exception occurs in the processing of the FASTA
	 *             entries
	 */
	public void calculateDiNucleotideOddsRatio(String username,
			String fastaResource, String csvOut) throws IOException, Exception {
		StoredResource resource = getAccess().createNewResourceJCR(username,
				csvOut);
		OutputStream out = resource.write();
		calculateDiNucleotideOddsRatio(username, fastaResource, out);
		out.close();
	}

	/**
	 * Calculates the di-nucleotide odds ratio for each FASTA entry from the
	 * input and writes the result in CSV format in the output stream.
	 * 
	 * @param fasta
	 *            {@link InputStream} to the FASTA or MULTI-FASTA file to be
	 *            processed
	 * @param csvOut
	 *            {@link OutputStream} in which the results in form of CSV will
	 *            be printed
	 * @throws IOException
	 *             in case the {@link InputStream} could not be read from or
	 *             {@link OutputStream} could not be written to
	 * @throws Exception
	 *             when an exception occurs in the processing of the FASTA
	 *             entries
	 */
	public void calculateDiNucleotideOddsRatio(InputStream fasta,
			OutputStream csvOut) throws IOException, Exception {
		final PrintWriter writer = new PrintWriter(csvOut);

		writer.println(CSV_HEADER);
		processFastaStream(fasta, new OnFastaAvailable() {
			@Override
			public void process(FASTAEntry fastaEntry) throws Exception {

				Map<String, DNASequence> fasta = FastaReaderHelper
						.readFastaDNASequence(new ByteArrayInputStream(
								fastaEntry.getEntryContent().getBytes(
										fastaEntry.getEncoding())));

				Map.Entry<String, DNASequence> firstEntry = fasta.entrySet()
						.iterator().next();
				if (firstEntry != null) {
					DiNucInfo info = new DiNucInfo();
					info.process(fastaEntry.getSequenceData());
					String fastaId = firstEntry.getKey();
					writer.println(String.format(CSV_ROW, fastaId,
							info.probDin(DiNucInfo.AA),
							info.probDin(DiNucInfo.AC),
							info.probDin(DiNucInfo.CC),
							info.probDin(DiNucInfo.CA),
							info.probDin(DiNucInfo.GA),
							info.probDin(DiNucInfo.AG),
							info.probDin(DiNucInfo.AT),
							info.probDin(DiNucInfo.CG),
							info.probDin(DiNucInfo.GC),
							info.probDin(DiNucInfo.TA)));
				}
			}
		});
	}
}
