<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List,edu.wustl.catissuecore.util.global.Constants,edu.wustl.catissuecore.util.global.Utility"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ page import="edu.wustl.catissuecore.util.global.Constants" %>
<%@ page import="edu.wustl.catissuecore.domain.Participant" %>


 <table cellpadding="0" cellspacing="0" border="0" width ="100%">
	
	<tr>
		<%if(conflictStatus.equals(CaTIESConstants.STATUS_PARTICIPANT_CONFLICT))
			{
		%>
			<td width = "30%">
				&nbsp;
			</td>
		<%} else {%>
			<td width = "50%">
				&nbsp;
			</td>
		<%}%>

		<td nowrap class="formFieldNoBorders" >
		
		<%if(conflictStatus.equals(CaTIESConstants.STATUS_PARTICIPANT_CONFLICT))
			{
		%>
			<html:button styleClass="actionButton" 
					styleId="useSelPart"
					property="" 
					title=""
					value="Use Selected Participant" 
					disabled="<%=useSelPartDisable%>" 
					onclick="onButtonClick('createNewSCG')">
 	    
	     	</html:button>
		<%}%>

	    </td>
		<td width = "5%">
			&nbsp;
		</td>
	    
	    <td nowrap class="formFieldNoBorders">
	    	<html:button styleClass="actionButton" 
						styleId="useSelSCG"
						property="" 
						title=""
						value="Use Selected SCG" 
						disabled="<%=useSelSCGDisable%>" 
						onclick="onButtonClick('associateSCG')">
									  				     	    
		    </html:button>
		</td>
		<td width = "5%">
			&nbsp;
		</td>

		<td nowrap class="formFieldNoBorders"> 
		<%if(conflictStatus.equals(CaTIESConstants.STATUS_PARTICIPANT_CONFLICT))
			{
		%>
			<html:button styleClass="actionButton" 
					styleId="crtNewPart"
					property="" 
					title=""
					value="Create New Participant"
					disabled="<%=crtNewPartDisable%>" 
					onclick="onButtonClick('creatNewParticipant')">
	     	</html:button>
		<%} else
		 	 {	
		%>
			<html:button styleClass="actionButton"  
					styleId="crtNewSCG"
					property="" 
					title=""
					value="Create New SCG"
					disabled="<%=crtNewSCGDisable%>" 
					onclick="onButtonClick('createNewSCG')">
	     	</html:button>

		<%}%>

	    </td>
		<td width = "5%">
			&nbsp;
		</td>
	</tr>
</table>

