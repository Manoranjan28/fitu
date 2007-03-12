package org.commonfarm.dao;

import org.commonfarm.Constant;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class DAOTest extends AbstractTransactionalDataSourceSpringContextTests {
	/**
	 * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[]{Constant.DEFAULT_CONTEXT, Constant.DEFAULT_TEST_CONTEXT};
	}
}
