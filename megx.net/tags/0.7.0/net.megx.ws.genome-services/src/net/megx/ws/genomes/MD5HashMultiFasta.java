package net.megx.ws.genomes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Map;

import javax.jcr.RepositoryException;

import net.megx.storage.StorageException;
import net.megx.storage.StoredResource;
import net.megx.ws.genomes.resources.WorkspaceAccess;

import org.apache.commons.codec.digest.DigestUtils;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;

public class MD5HashMultiFasta extends BaseGenomeService {

	public MD5HashMultiFasta(WorkspaceAccess access) {
		super(access);
	}

	public void calculateMD5Hash(String username, String inputFasta)
			throws RepositoryException, URISyntaxException, StorageException,
			IOException, Exception {
		final StoredResource resource = getAccess().getResourceJCR(username,
				inputFasta);
		InputStream is = resource.read();
		/*
		 * FIXME: This will not work for large FASTA files because of two
		 * reasons: - the MD5 hash is stored in RAM, so we cannot keep a lot of
		 * hashes in memory - Jackrabbit cannot store large data, so we must
		 * keep the MD5 hash somewhere in the repository.
		 */
		final StringWriter sw = new StringWriter();

		processFastaStream(is, new OnFastaAvailable() {

			@Override
			public void process(FASTAEntry fastaEntry) throws Exception {
				Map<String, DNASequence> fasta = FastaReaderHelper
						.readFastaDNASequence(new ByteArrayInputStream(
								fastaEntry.getEntryContent().getBytes(
										fastaEntry.getEncoding())));

				Map.Entry<String, DNASequence> firstEntry = fasta.entrySet()
						.iterator().next();
				if (firstEntry != null) {
					String identifier = firstEntry.getKey();
					String md5Hash = DigestUtils.md5Hex(fastaEntry
							.getEntryContent());

					sw.write(String.format("%s, %s%n", identifier, md5Hash));

				}

			}
		});
		is.close();
		
		resource.getResourceMeta().setAttr("hash", sw);
		resource.dispose();
	}

	public String getMd5Hash(String username, String inputFasta)
			throws RepositoryException, URISyntaxException, StorageException,
			IOException, Exception {
		StoredResource resource = getAccess().getResourceJCR(username,
				inputFasta);
		String md5 = (String)resource.getResourceMeta().getAttr("hash");
		return md5;
	}
}
