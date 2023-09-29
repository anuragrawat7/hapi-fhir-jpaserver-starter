package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

public class FhirBundleExample {
	public static void main(String[] args) {
		FhirContext ctx = FhirContext.forR4();

		Bundle bundle = new Bundle();
		bundle.setType(Bundle.BundleType.TRANSACTION);

//		for (int i = 1; i <= 2; i++) {
//			Patient patient = new Patient();

			// Add the patient as an entry. This entry is a POST with an
			// If-None-Exist header (conditional create) meaning that it
			// will only be created if there isn't already a Patient with
			// the identifier 12345
//			bundle.addEntry()
//				.setFullUrl(patient.getIdElement().getValue())
//				.setResource(patient)
//				.getRequest()
//				.setUrl("Patient")
//				.setIfNoneExist("identifier=http://acme.org/mrns|12345")
//				.setMethod(Bundle.HTTPVerb.POST);

//			patient.addName().setFamily("Rawat").addGiven("Anurag");
//			bundle.addEntry()
//				.setResource(patient)
//				.getRequest()
//				.setUrl("Patient/" + i)
//				.setMethod(Bundle.HTTPVerb.POST);
//		}

		IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080/fhir");
//		Bundle responseBundle = client.transaction().withBundle(bundle).execute();

		//To search a patient with id
		Bundle responseBundle = client.search().forResource(Patient.class).where(Patient.RES_ID.exactly().identifier("1"))
			.returnBundle(Bundle.class).execute();

		for (Bundle.BundleEntryComponent entry : responseBundle.getEntry()) {
			Patient patient = (Patient) entry.getResource();
			String patientJson = ctx.newJsonParser().encodeResourceToString(patient);
			System.out.println(patientJson);
		}
	}
}
