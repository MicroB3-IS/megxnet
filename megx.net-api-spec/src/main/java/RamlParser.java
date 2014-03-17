import java.util.Iterator;
import java.util.List;

import org.raml.parser.rule.ValidationResult;
import org.raml.parser.visitor.RamlValidationService;


public class RamlParser {

	
	public static void main(String[] args) {
		String ramlBuffer = "file:///e:/workspace/parent-microb3-is/megx.net-api-spec/src/main/raml/mb3-is-v1.raml";
		
		 final List<ValidationResult> results = RamlValidationService.createDefault().validate(ramlBuffer, "");
		 for (Iterator<ValidationResult> iterator = results.iterator(); iterator.hasNext();) {
			ValidationResult validationResult = (ValidationResult) iterator
					.next();
			System.out.println( validationResult + " " + validationResult.getIncludeContext()  );
		}
	}
	
}
