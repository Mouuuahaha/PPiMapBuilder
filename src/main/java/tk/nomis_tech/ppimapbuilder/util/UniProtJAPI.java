package tk.nomis_tech.ppimapbuilder.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import uk.ac.ebi.kraken.uuw.services.remoting.AdminService;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.PagingService;
import uk.ac.ebi.kraken.uuw.services.remoting.ProteinDataQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.ProteinDataQueryServiceClientImpl;
import uk.ac.ebi.kraken.uuw.services.remoting.RemoteDataAccessException;
import uk.ac.ebi.kraken.uuw.services.remoting.UniParcQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniParcQueryServiceClientImpl;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryServiceClientImpl;
import uk.ac.ebi.kraken.uuw.services.remoting.UniRefQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniRefQueryServiceClientImpl;

public enum UniProtJAPI {
	factory;

	private XmlBeanFactory xmlBeanFactory;

	private UniProtJAPI() {
		try {
//			this.xmlBeanFactory = new XmlBeanFactory(
//				new OsgiBundleResource(FrameworkUtil.getBundle(getClass()), "/remotingClient.xml")
//			);
//			Properties localProperties = new Properties();
//			localProperties.load(
//				new OsgiBundleResource(FrameworkUtil.getBundle(getClass()), "/remote_client.properties").getInputStream()
//			);
			
			this.xmlBeanFactory = new XmlBeanFactory(
				new ClassPathResource("/remotingClient.xml", uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI.class.getClassLoader())
			);
			Properties localProperties = new Properties();
			localProperties.load(
				new ClassPathResource("/remote_client.properties", uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI.class.getClassLoader()).getInputStream()
			);
			
			PropertyPlaceholderConfigurer localPropertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			localPropertyPlaceholderConfigurer.setProperties(localProperties);
			/*ClassPathResource localClassPathResource = new ClassPathResource(
					"uniprotjapi.properties");
			if (localClassPathResource.exists())
				localPropertyPlaceholderConfigurer
						.setLocation(localClassPathResource);*/
			localPropertyPlaceholderConfigurer
					.postProcessBeanFactory(this.xmlBeanFactory);
		} catch (IOException localIOException) {
			throw new RemoteDataAccessException(
					"Unable to start xmlBeanFactory", localIOException);
		}
	}

	public EntryRetrievalService getEntryRetrievalService() {
		return (EntryRetrievalService) this.xmlBeanFactory
				.getBean("entryRetrievalProxy");
	}

	public UniProtQueryService getUniProtQueryService() {
		UniProtQueryService localUniProtQueryService = (UniProtQueryService) this.xmlBeanFactory
				.getBean("queryServiceProxy");
		PagingService localPagingService = (PagingService) this.xmlBeanFactory
				.getBean("pagingServiceProxy");
		EntryRetrievalService localEntryRetrievalService = (EntryRetrievalService) this.xmlBeanFactory
				.getBean("entryRetrievalProxy");
		UniProtQueryServiceClientImpl localUniProtQueryServiceClientImpl = new UniProtQueryServiceClientImpl(
				localUniProtQueryService, localPagingService,
				localEntryRetrievalService);
		return localUniProtQueryServiceClientImpl;
	}

	public UniRefQueryService getUniRefQueryService() {
		UniRefQueryService localUniRefQueryService = (UniRefQueryService) this.xmlBeanFactory
				.getBean("uniRefQueryServiceProxy");
		EntryRetrievalService localEntryRetrievalService = (EntryRetrievalService) this.xmlBeanFactory
				.getBean("entryRetrievalProxy");
		UniRefQueryServiceClientImpl localUniRefQueryServiceClientImpl = new UniRefQueryServiceClientImpl(
				localUniRefQueryService, localEntryRetrievalService);
		return localUniRefQueryServiceClientImpl;
	}

	public ProteinDataQueryService getProteinDataQueryService() {
		ProteinDataQueryService localProteinDataQueryService = (ProteinDataQueryService) this.xmlBeanFactory
				.getBean("pdQueryServiceProxy");
		PagingService localPagingService = (PagingService) this.xmlBeanFactory
				.getBean("pdPagingServiceProxy");
		EntryRetrievalService localEntryRetrievalService = (EntryRetrievalService) this.xmlBeanFactory
				.getBean("entryRetrievalProxy");
		ProteinDataQueryServiceClientImpl localProteinDataQueryServiceClientImpl = new ProteinDataQueryServiceClientImpl(
				localProteinDataQueryService, localPagingService,
				localEntryRetrievalService);
		return localProteinDataQueryServiceClientImpl;
	}

	public UniParcQueryService getUniParcQueryService() {
		UniParcQueryService localUniParcQueryService = (UniParcQueryService) this.xmlBeanFactory
				.getBean("uniParcQueryServiceProxy");
		PagingService localPagingService = (PagingService) this.xmlBeanFactory
				.getBean("uniParcPagingServiceProxy");
		EntryRetrievalService localEntryRetrievalService = (EntryRetrievalService) this.xmlBeanFactory
				.getBean("entryRetrievalProxy");
		UniParcQueryServiceClientImpl localUniParcQueryServiceClientImpl = new UniParcQueryServiceClientImpl(
				localUniParcQueryService, localPagingService,
				localEntryRetrievalService);
		return localUniParcQueryServiceClientImpl;
	}

	public String getVersion() throws RemoteDataAccessException {
		AdminService localAdminService = (AdminService) this.xmlBeanFactory
				.getBean("adminServiceProxy");
		return localAdminService.getVersion();
	}
}