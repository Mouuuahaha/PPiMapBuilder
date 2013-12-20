package tk.nomis_tech.ppimapbuilder;

import javax.swing.SwingUtilities;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import tk.nomis_tech.ppimapbuilder.ui.QueryWindow;
import tk.nomis_tech.ppimapbuilder.util.UniProtJAPI;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;

/**
 * The interaction query menu
 */
public class PMBQueryMenuTask extends AbstractTask {

	private QueryWindow qw;

	public PMBQueryMenuTask(QueryWindow queryWindow) {
		this.qw = queryWindow;
	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		System.out.println("---");
		System.out.println("Get information from Uniprot...");
		EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
		UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry("P04040");
		if (entry != null) {
			System.out.println(entry.toString());
		}
		System.out.println("---");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				/*
				try {
					PsicquicRegistry reg = new PsicquicRegistry();
					qw.updateLists(reg.getServices());

					qw.setVisible(true);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to get PSICQUIC databases");
				}
				*/
			}
		});
	}

}
