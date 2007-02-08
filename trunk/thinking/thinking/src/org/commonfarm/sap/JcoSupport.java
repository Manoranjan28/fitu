package org.commonfarm.sap;


/**
 * Convenient super class for JCO data access objects.
 *
 * @author David Yang
 */
public class JcoSupport {

	private JcoTemplate sapJCOTemplate;


	/**
	 * Set the JCOSource to be used by this DAO.
	 * Will automatically create a SapJCOTemplate for the given JCOSource.
	 * @see #createSapJCOTemplate
	 * @see #setSapJCOTemplate
	 */
	public final void setJCOSource(JcoSource jcoSource) {
	  this.sapJCOTemplate = createSapJCOTemplate(jcoSource);
	}

	/**
	 * Create a SapJCOTemplate for the given JCOSource.
	 */
	protected JcoTemplate createSapJCOTemplate(JcoSource jcoSource) {
		return new JcoTemplate(jcoSource);
	}

	/**
	 * Return the JCOSource used by this DAO.
	 */
	public final JcoSource getJCOSource() {
		return (this.sapJCOTemplate != null ? this.sapJCOTemplate.getJcoSource() : null);
	}

	/**
	 * Set the SapJCOTemplate for this DAO explicitly,
	 * as an alternative to specifying a JCOSource.
	 * @see #setJCOSource
	 */
	public final void setSapJCOTemplate(JcoTemplate sapJCOTemplate) {
		this.sapJCOTemplate = sapJCOTemplate;
	}

	/**
	 * Return the SapJCOTemplate for this DAO,
	 * pre-initialized with the JCOSource or set explicitly.
	 */
	public final JcoTemplate getSapJCOTemplate() {
	  return sapJCOTemplate;
	}
}
