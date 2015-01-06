package net.megx.ws.genomes;


public class DiNucInfo {
	public static int AA=0,  AC=1,  AG=2,  AT=3,  AN=4;
	public static int CA=5,  CC=6,  CG=7,  CT=8,  CN=9;
	public static int GA=10, GC=11, GG=12, GT=13, GN=14;
	public static int TA=15, TC=16, TG=17, TT=18, TN=19;
	public static int NA=20, NC=21, NG=22, NT=23, NN=24;
	
	public static String [] NUCLEOTIDES = {"A","C","G","T","N"};
	
	
	public static int A=0, C=1, G=2, T=3, N=4;
	
	private static int [][] PAIRS = {
	    //AA   AC     AG    AT     AN
		 {TT}, {GT}, {CT},  {},    {},
		//CA   CC     CG    CT     CN
		 {TG}, {GG},  {},   {AG},  {},
		//GA   GC     GG    GT     GN
		 {TC}, {},    {CC}, {AC},  {},
		//TA   TC     TG    TT     TN
		 {},   {GA},  {CA}, {AA},  {},
		//NA   NC     NG    NT     NN
		 {},   {},    {},   {},    {}
	};
	
	
	private int [] diNucCounts = new int [25];
	
	private int [] nucCounts = new int [5];
	private int length;
	private int total;
	
	private void updateLength(){
		length = 0;
		for(int i = 0; i < 4; i++){
			length+=nucCounts[i];
		}
	}
	
	private void updateTotal(){
		total=0;
		for(int c: diNucCounts){
			total+=c;
		}
	}
	
	public void process(String seq){
		int prev = -1;
		int curr = -1;
		int offset = -1;
		for(int i = 0; i < seq.length(); i++){
			switch(seq.charAt(i)){
			case 'a':
			case 'A':
				curr = 0;
				nucCounts[A]++;
				break;
			case 'c':
			case 'C':
				curr = 1;
				nucCounts[C]++;
				break;
			case 'g':
			case 'G':
				curr = 2;
				nucCounts[G]++;
				break;
			case 't':
			case 'T':
				curr = 3;
				nucCounts[T]++;
				break;
			case 'n':
			case 'N':
				curr = 4;
				nucCounts[N]++;
				break;
			default:
				break;
			}
			
			if(prev != -1){
				offset = prev*5+curr;
				diNucCounts[offset]++;
			}
			prev = curr;
			
		}
		updateLength();
		updateTotal();
	}
	
	public int length(){
		return length;
	}
	
	private double frequency(int nuc){
		return nucCounts[nuc]/(double)length;
	}
	
	public double frequencyA(){
		return frequency(A);
	}
	public double frequencyC(){
		return frequency(C);
	}
	public double frequencyG(){
		return frequency(G);
	}
	public double frequencyT(){
		return frequency(T);
	}
	public double frequencyN(){
		return frequency(N);
	}
	
	
	private double frequencyXR(int nuc){
		if(nuc == A || nuc == T){
			return (frequencyA()+frequencyT())/2;
		}else{
			return (frequencyC()+frequencyG())/2;
		}
	}
	
	public double frequencyAR(){
		return frequencyXR(A);
	}
	public double frequencyTR(){
		return frequencyXR(T);
	}
	public double frequencyCR(){
		return frequencyXR(C);
	}
	public double frequencyGR(){
		return frequencyXR(G);
	}
	
	public double frequencyDiNuc(int diNuc){
		return diNucCounts[diNuc]/(double)total;
	}
	
	private double frequencyDinN(int din1, int din2){
		return (frequencyDiNuc(din1)+frequencyDiNuc(din2))/2;
	}
	
	public double frequencyDinN(int din){
		int [] other = PAIRS[din];
		if(other.length > 0){
			return frequencyDinN(din, other[0]);
		}else{
			return frequencyDiNuc(din);
		}
	}
	
	public double probDin(int din){
		double dinFreq = frequencyDinN(din);
		int nuc1 = din/5;
		int nuc2 = din%5;
		return dinFreq/(frequencyXR(nuc1)*frequencyXR(nuc2));
	}
	
	public static String getName(int din){
		return NUCLEOTIDES[din/5]+NUCLEOTIDES[din%5];
	}
	
	public static String nucleotide(int n){
		return NUCLEOTIDES[n];
	}
	
	public static void main(String[] args) {
		String seq = "AACACATGAAACAACATTACACANANAAACATANNNACTGA";
		
		DiNucInfo info = new DiNucInfo();
		info.process(seq);
		for(int i = 0; i < 20; i++){
			if(i%5 == 4)
				continue;
			System.out.println("p"+DiNucInfo.getName(i)+": " + info.probDin(i));
		}
	}
	
}
