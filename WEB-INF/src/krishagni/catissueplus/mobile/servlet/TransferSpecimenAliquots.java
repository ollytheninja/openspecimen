package krishagni.catissueplus.mobile.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.wustl.catissuecore.processor.CatissueLoginProcessor;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.domain.LoginCredentials;
import edu.wustl.domain.LoginResult;
import edu.wustl.processor.LoginProcessor;

import org.json.JSONException;
import org.json.JSONObject;
import edu.wustl.common.beans.SessionDataBean;
import edu.wustl.dao.DAO;
import edu.wustl.catissuecore.util.global.AppUtility;
import edu.wustl.catissuecore.bizlogic.SpecimenEventParametersBizLogic;
import edu.wustl.common.exception.BizLogicException;
import edu.wustl.dao.exception.DAOException;

public class TransferSpecimenAliquots  extends HttpServlet {
	/**
	 *
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException
	{
		doPost(req, res);
	}

	
	/**
	 * This method is used to download files saved in database.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		
		JSONObject returnedJObject= new JSONObject();
		String msg;
		String successString;

		try{
			final SessionDataBean sessionData = (SessionDataBean) request.getSession().getAttribute(
					Constants.SESSION_DATA);
			
			SpecimenEventParametersBizLogic bizLogic = new SpecimenEventParametersBizLogic();
			int pos1 = Integer.parseInt(request.getParameter("positionX"));
			int pos2 = Integer.parseInt(request.getParameter("positionY"));
			String specimenLabel = request.getParameter("specimenLable");
			String containerName = request.getParameter("containerName");
			
			msg = bizLogic.specimenEventTransferFromMobile(sessionData,specimenLabel,containerName,pos1,pos2);
			successString = "success";
		}catch(BizLogicException ex){
			msg = ex.getErrorKey().getErrorMessage();
			successString = "failure";
		}catch(DAOException ex){
			msg = ex.getErrorKey().getErrorMessage();
			successString = "failure";
		}
		
		try {
			returnedJObject.put("msg", msg);
			returnedJObject.put("success", successString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/json");
    	response.getWriter().write(returnedJObject.toString());

	    
		
	}
	

}
