package com.revature.runnable;


import com.revature.dao.AssociateDaoHibernate;
import com.revature.dao.BatchDaoHibernate;
import com.revature.dao.ClientDaoImpl;
import com.revature.dao.CurriculumDaoImpl;
import com.revature.dao.InterviewDaoHibernate;
import com.revature.dao.MarketingStatusDaoHibernate;
import com.revature.dao.TechDaoHibernate;
//import com.revature.services.PersistentServiceDelegator;

/**
 * Used to handle caching on server startup.
 * Used to prevent server timeout on Virtual Machine (VM)
 * @author Antony Lulciuc
 */
public class PSDCacheRunner implements Runnable {
	public static final long DEFAULT_CACHE_START = 30000;
	private long delayedStartTime = DEFAULT_CACHE_START;
	
	public PSDCacheRunner() { };

	/**
	 * Performs caching operations for persistent data used in application
	 */
	@Override
	public void run() {
		// Check if data used for caching is valid
		if (hasValidFields()) {
			cache();
		}
	}

	///
	//	PRIVATE METHODS
	///

	/**
	 * Determine if arguments supplied in constructor are valid
	 * @return true if delayedStartTime > -1 and psd not null else false
	 */
	private boolean hasValidFields() {
		return delayedStartTime > -1 ;
	}

	/**
	 * Delays caching process by specified interval [0, delayedStartTime]
	 */
	@SuppressWarnings("unused")
	private void delay() {
		long current = System.currentTimeMillis();
		long start = delayedStartTime + current;

		// Set current time
		current = System.currentTimeMillis();

		// Loop until current time exceeds or equals start (begin caching)
		while (current < start) {
			current += System.currentTimeMillis() - current;

			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				// do nothing
			}
		}
	}

	/**
	 * Performs caching process
	 */
	private void cache() {

	            // perform caching
    	
	            long startTime = System.nanoTime();
	            new AssociateDaoHibernate().cacheAllAssociates();
	            long endTime = System.nanoTime();
	            double elapsedTime = ((double)(endTime -startTime))/1000000000,total=elapsedTime;
	            System.out.println("Associates caching time: "+elapsedTime+" seconds");

	            startTime = System.nanoTime();
	            new BatchDaoHibernate().cacheAllBatches();
	            endTime = System.nanoTime();
	            elapsedTime = ((double)(endTime -startTime))/1000000000; total+=elapsedTime;
	            System.out.println("Batches caching time: "+elapsedTime+" seconds");

	            startTime = System.nanoTime();
	            new ClientDaoImpl().cacheAllClients();
	            endTime = System.nanoTime();
	            elapsedTime = ((double)(endTime -startTime))/1000000000;total+=elapsedTime;
	            System.out.println("Clients caching time: "+elapsedTime+" seconds");

	            startTime = System.nanoTime();
	            new CurriculumDaoImpl().cacheAllCurriculms();
	            endTime = System.nanoTime();
	            elapsedTime = ((double)(endTime -startTime))/1000000000;total+=elapsedTime;
	            System.out.println("Curriculums caching time: "+elapsedTime+" seconds");

	            startTime = System.nanoTime();
	            new MarketingStatusDaoHibernate().cacheAllMarketingStatuses();
	            endTime = System.nanoTime();
	            elapsedTime = ((double)(endTime -startTime))/1000000000;total+=elapsedTime;
	            System.out.println("MarketingStatuses caching time: "+elapsedTime+" seconds");
	            
	            startTime = System.nanoTime(); // to do
                new InterviewDaoHibernate().cacheAllInterviews();
                endTime = System.nanoTime();
                elapsedTime = ((double)(endTime -startTime))/1000000000;total+=elapsedTime;
                System.out.println("Interviews caching time: "+elapsedTime+" seconds");
	            
	            startTime = System.nanoTime();
	            new TechDaoHibernate().cacheAllTechs();
	            endTime = System.nanoTime();
	            elapsedTime = ((double)(endTime -startTime))/1000000000;total+=elapsedTime;
	            System.out.println("Technologies caching time: "+elapsedTime+" seconds");
	            System.out.println("Total caching time: "+total+" seconds");
	}
}
