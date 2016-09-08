package org.openo.sdno.vpc.mocoserver;

import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;

public class SbiAdapterSuccessServer extends MocoHttpServer{
	
	 private static final String CREATE_VPC_SUCCESS_FILE =
	            "src/integration-test/resources/adapter/createvpcsuccess.json";
	 
	 private static final String DELETE_VPC_SUCCESS_FILE =
	            "src/integration-test/resources/adapter/deletevpcsuccess.json";
	 
	 private static final String CREATE_SUBNET_SUCCESS_FILE =
	            "src/integration-test/resources/adapter/createsubnetsuccess.json";
	 private static final String DELETE_SUBNET_SUCCESS_FILE =
	            "src/integration-test/resources/adapter/deletesubnetsuccess.json";
	 
	 
	public SbiAdapterSuccessServer(String configFile) {
        super(configFile);
    }
	
	 public SbiAdapterSuccessServer() {

	    }

	 @Override
	    public void addRequestResponsePairs() {
		 this.addRequestResponsePair(CREATE_VPC_SUCCESS_FILE, new CreateVpcSuccessInResponseHandler());
		 this.addRequestResponsePair(DELETE_VPC_SUCCESS_FILE, new CreateVpcSuccessInResponseHandler());		 
		 this.addRequestResponsePair(CREATE_SUBNET_SUCCESS_FILE, new CreateVpcSuccessInResponseHandler());
		 this.addRequestResponsePair(DELETE_SUBNET_SUCCESS_FILE, new CreateVpcSuccessInResponseHandler());
		 
	 }
	 private class CreateVpcSuccessInResponseHandler extends MocoResponseHandler {
		 @Override
	        public void processRequestandResponse(HttpRquestResponse httpObject) {
	            HttpRequest req = httpObject.getRequest();
	            HttpResponse res = httpObject.getResponse();

	            System.out.println(req);
	            System.out.println(res);
		 }
	 }
}
