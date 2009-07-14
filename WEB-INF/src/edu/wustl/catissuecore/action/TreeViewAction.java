/**
 * <p>
 * Title: TreeViewAction Class>
 * <p>
 * Description: TreeViewAction is used to display the query results tree view.
 * </p>
 * Copyright: Copyright (c) year Company: Washington University, School of
 * Medicine, St. Louis.
 *
 * @author Gautam Shetty
 * @version 1.00
 */

package edu.wustl.catissuecore.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.common.util.logger.Logger;

/**
 * TreeViewAction is used to display the query results tree view.
 *
 * @author gautam_shetty
 */
public class TreeViewAction extends Action
{

	/**
	 * Generic logger.
	 */
	private transient Logger logger = Logger.getCommonLogger(TreeViewAction.class);

	/**
	 * Overrides the executeSecureAction method of SecureAction class.
	 * @param mapping object of ActionMapping.
	 * @param form object of Action Form.
	 * @param request object of HttpServletRequest
	 * @param response object of HttpServletResponse
	 * @throws Exception generic exception
	 * @return ActionForward : ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Sets the pageOf attribute (for Add,Edit or Query Interface)
		final String pageOf = request.getParameter(Constants.PAGE_OF);
		this.logger.debug("pageOf in treeview..." + pageOf);
		request.setAttribute(Constants.PAGE_OF, pageOf);
		if (pageOf.equals(Constants.PAGE_OF_TISSUE_SITE))
		{
			final HttpSession session = request.getSession();
			final String cdeName = (String) session.getAttribute(Constants.CDE_NAME);
			session.removeAttribute(Constants.CDE_NAME);
			request.setAttribute(Constants.CDE_NAME, cdeName);
		}
		return mapping.findForward(Constants.SUCCESS);
	}

}
