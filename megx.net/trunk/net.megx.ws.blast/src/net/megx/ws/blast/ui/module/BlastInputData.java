package net.megx.ws.blast.ui.module;

import java.io.File;

import org.chon.web.api.Request;

public class BlastInputData {
		private String seq;
		private File file;
		private String blastDb;
		private String evalueCutoff;
		
		public BlastInputData(Request req) {
			this.seq = req.get("seq");
			this.blastDb = req.get("blastDb");
			if(req.getFiles() != null && req.getFiles().size()>0) {
				this.file = req.getFiles().get(0).getFile();
			}
			this.evalueCutoff = req.get("evalue_cutoff");
		}

		public String getSeq() {
			return seq;
		}

		public File getFile() {
			return file;
		}

		public String getBlastDb() {
			return blastDb;
		}

		public String getEvalueCutoff() {
			return evalueCutoff;
		}
	}