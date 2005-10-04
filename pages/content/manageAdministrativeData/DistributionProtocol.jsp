<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="edu.wustl.catissuecore.util.global.Constants"%>
<%@ page import="edu.wustl.catissuecore.actionForm.DistributionProtocolForm"%>
<%@ page import="java.util.*"%>
<%@ page import="edu.wustl.common.beans.NameValueBean"%>
<%@ page import="edu.wustl.catissuecore.util.global.Utility"%>
<%@ page import="java.util.*"%>

<head>
<script src="jss/script.js" type="text/javascript"></script>
<%
	
	List tissueSiteList = (List) request.getAttribute(Constants.TISSUE_SITE_LIST);

	List pathologyStatusList = (List) request.getAttribute(Constants.PATHOLOGICAL_STATUS_LIST);
	
    String operation = (String) request.getAttribute(Constants.OPERATION);
    String formName;


    boolean readOnlyValue;
    if (operation.equals(Constants.EDIT))
    {
        formName = Constants.DISTRIBUTIONPROTOCOL_EDIT_ACTION;
        readOnlyValue = false;
    }
    else
    {
        formName = Constants.DISTRIBUTIONPROTOCOL_ADD_ACTION;
        readOnlyValue = false;
    }
    
		Object obj = request.getAttribute("distributionProtocolForm");
		int noOfRows=1;
		Map map = null;
		DistributionProtocolForm form = null;
		if(obj != null && obj instanceof DistributionProtocolForm)
		{
			form = (DistributionProtocolForm)obj;
			map = form.getValues();
			noOfRows = form.getCounter();
		}
		
		
			String reqPath = (String)request.getAttribute(Constants.REQ_PATH);
		String appendingPath = "/DistributionProtocol.do?operation=add&pageOf=pageOfDistributionProtocol";
		if (reqPath != null)
			appendingPath = reqPath + "|/DistributionProtocol.do?operation=add&pageOf=pageOfDistributionProtocol";
	
	   	if(!operation.equals("add") )
	   	{
	   		Object obj1 = request.getAttribute("distributionProtocolForm");
			if(obj1 != null && obj instanceof DistributionProtocolForm)
			{
				DistributionProtocolForm form1 = (DistributionProtocolForm)obj1;
		   		appendingPath = "/DistributionProtocolSearch.do?operation=search&pageOf=pageOfDistributionProtocol&systemIdentifier="+form1.getSystemIdentifier() ;
		   		System.out.println("---------- DP JSP appendingPath -------- : "+ appendingPath);
		   	}
	   	}

%>

<%@ include file="/pages/content/common/CommonScripts.jsp" %>

<SCRIPT LANGUAGE="JavaScript">


	var win = null;
	function NewWindow(mypage,myname,w,h,scroll)
	{
		LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		TopPosition = (screen.height) ? (screen.height-h)/2 : 0;

		settings =
			'height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable'
		win = open(mypage,myname,settings)
		if (win.opener == null)
			win.opener = self;
	}
//code for units end
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
	var search1='`';
	var insno=0;
</script>
<script language="JavaScript" type="text/javascript" src="jss/javaScript.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
	// functions for add more


//  function to insert a row in the inner block
function insRow(subdivtag)
{
		var val = parseInt(document.forms[0].counter.value);
		val = val + 1;
		document.forms[0].counter.value = val;
	var sname = "";

	var r = new Array(); 
	r = document.getElementById(subdivtag).rows;
	var q = r.length;
//	var x=document.getElementById(subdivtag).insertRow(q);
	var x=document.getElementById(subdivtag).insertRow(1);
	
	var subdivname = ""+ subdivtag;

	// srno
	var spreqno=x.insertCell(0)
	spreqno.className="tabrightmostcell";
	var rowno=(q);
	var identifier = "value(SpecimenRequirement:" + rowno +"_systemIdentifier)";
	var cell1 = "<input type='hidden' name='" + identifier + "' value='' id='" + identifier + "'>";
	spreqno.innerHTML="" + rowno+"." + cell1;
	
	//type
	var spreqtype=x.insertCell(1)
	spreqtype.className="formField";
	sname="";
	objname = "value(SpecimenRequirement:" + rowno + "_specimenClass)";
	var specimenClassName = objname;
//value(SpecimenRequirement:`_quantityIn)	
	
	var objunit = "value(SpecimenRequirement:"+rowno+"_unitspan)";
	
	sname = "<select name='" + objname + "' size='1' onchange=changeUnit('" + objname + "','" + objunit +"') class='formFieldSized10' id='" + objname + "'>";
	<%for(int i=0;i<specimenClassList.size();i++)
	{
		String specimenClassLabel = "" + ((NameValueBean)specimenClassList.get(i)).getName();
		String specimenClassValue = "" + ((NameValueBean)specimenClassList.get(i)).getValue();
	%>
		sname = sname + "<option value='<%=specimenClassValue%>'><%=specimenClassLabel%></option>";
	<%}%>
	sname = sname + "</select>";
	 
	spreqtype.innerHTML="" + sname;
	
	//subtype
	var spreqsubtype=x.insertCell(2)
	spreqsubtype.className="formField";
	sname="";
	objname = "value(SpecimenRequirement:"+rowno+"_specimenType)";
	var functionName = "onSubTypeChangeUnit('" + specimenClassName + "',this,'" + objunit + "')" ;
	
	sname= "<select name='" + objname + "' size='1' class='formFieldSized10' id='" + objname + "' onChange=" + functionName + " >";
			
	sname = sname + "<option value='-1'><%=Constants.SELECT_OPTION%></option>";
	sname = sname + "</select>"
	
	spreqsubtype.innerHTML="" + sname;
	
	//tissuesite
	var spreqtissuesite=x.insertCell(3)
	spreqtissuesite.className="formField";
	sname="";
	objname = "value(SpecimenRequirement:"+rowno+"_tissueSite)";
	
	sname = "<select name='" + objname + "' size='1' class='formFieldSized35' id='" + objname + "'>";
	<%for(int i=0;i<tissueSiteList.size();i++)
	{%>
		sname = sname + "<option value='<%=((NameValueBean)tissueSiteList.get(i)).getValue()%>'><%=((NameValueBean)tissueSiteList.get(i)).getName()%></option>";
	<%}%>
	sname = sname + "</select>"
	var url = "ShowFramedPage.do?pageOf=pageOfTissueSite&propertyName="+objname;			
	sname = sname + "<a href='#' onclick=javascript:NewWindow('" + url + "','name','250','330','no');return false>";
	sname = sname + "<img src='images\\Tree.gif' border='0' width='26' height='22'></a>";
	
	spreqtissuesite.innerHTML="" + sname;
	
	//pathologystatus
	var spreqpathologystatus=x.insertCell(4)
	spreqpathologystatus.className="formField";
	
	sname="";
	objname = "value(SpecimenRequirement:"+rowno+"_pathologyStatus)";
	
	sname="<select name='" + objname + "' size='1' class='formFieldSized10' id='" + objname + "'>";
	<%for(int i=0;i<pathologyStatusList.size();i++)
	{%>
		sname = sname + "<option value='<%=((NameValueBean)pathologyStatusList.get(i)).getValue()%>'><%=((NameValueBean)pathologyStatusList.get(i)).getName()%></option>";
	<%}%>
	sname = sname + "</select>";
	
	spreqpathologystatus.innerHTML="" + sname;
	
	//qty
	var spreqqty=x.insertCell(5)
	spreqqty.className="formField";
	sname="";
	objname = "value(SpecimenRequirement:"+rowno+"_quantityIn)";

	sname="<input type='text' name='" + objname + "' value='' class='formFieldSized5' id='" + objname + "'>"        	
	sname = sname + "&nbsp;<span id='" + objunit + "'>&nbsp;</span>"
					
	spreqqty.innerHTML="" + sname;
	
	//CheckBox
	var checkb=x.insertCell(6);
	checkb.className="formField";
	checkb.colSpan=2;
	sname="";
	var name = "chk_"+rowno;
	sname="<input type='checkbox' name='" + name +"' id='" + name +"' value='C'>"
	checkb.innerHTML=""+sname;
}

//-->
</SCRIPT>

<style>
	div#d1
	{
		display:none;
	}
	div#d999
	{
	 	display:none;
	}
</style>
</head>
<body>

<html:errors />
<html:messages id="messageKey" message="true" header="messages.header" footer="messages.footer">
	<%=messageKey%>
</html:messages>

<html:form action="<%=formName%>">

<!-- table 1 -->
<table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="100%">
<!-- NEW DISTRIBUTIONPROTOCOL ENTRY BEGINS-->
		<tr>
		<td colspan="3">
		<!-- table 4 -->
			<table summary="" cellpadding="3" cellspacing="0" border="0" width="96%">
				<tr>
					<td><html:hidden property="operation" value="<%=operation%>" /></td>
				</tr>
				
				<tr>
					<td><html:hidden property="systemIdentifier" /></td>
				</tr>

					<tr>
						<td class="formMessage" colspan="4">* indicates a required field</td>
					</tr>
<!-- page title -->					
					<tr>
						<td class="formTitle" height="20" colspan="4">
							<logic:equal name="operation" value="<%=Constants.ADD%>">
								<bean:message key="distributionprotocol.title"/>
							</logic:equal>
							<logic:equal name="operation" value="<%=Constants.EDIT%>">
								<bean:message key="distributionprotocol.editTitle"/>
							</logic:equal>
						</td>
					</tr>
					
<!-- principal investigator -->	
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel">
							<label for="principalInvestigatorId">
								<bean:message key="distributionprotocol.principalinvestigator" />
							</label>
						</td>
						
						<td class="formField">
							<html:select property="principalInvestigatorId" styleClass="formFieldSized" styleId="principalInvestigatorId" size="1">
								<html:options collection="<%=Constants.USERLIST%>" labelProperty="name" property="value"/>
							</html:select>
							&nbsp;
							<%
								String urlToGo = "/User.do?operation=add&pageOf=pageOfUserAdmin";
								String onClickPath = "changeUrl(this,'"+appendingPath+"')";
							%>
							<html:link page="<%=urlToGo%>" styleId="newUser" onclick="<%=onClickPath%>">
								<bean:message key="buttons.addNew" />
	 						</html:link>
						</td>
					</tr>

<!-- title -->						
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel">
							<label for="title">
								<bean:message key="distributionprotocol.protocoltitle" />
							</label>
						</td>
						<td class="formField" colspan=2>
							<html:text styleClass="formFieldSized" size="30" styleId="title" property="title" readonly="<%=readOnlyValue%>" />
						</td>
					</tr>

<!-- short title -->						
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel">
							<label for="shortTitle">
								<bean:message key="distributionprotocol.shorttitle" />
							</label>
						</td>
						<td class="formField" colspan=2>
							<html:text styleClass="formFieldSized" size="30" styleId="shortTitle" property="shortTitle" readonly="<%=readOnlyValue%>" />
						</td>
					</tr>
					
<!-- irb id -->						
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel">
							<label for="irbID">
								<bean:message key="distributionprotocol.irbid" />
							</label>
						</td>
						<td class="formField" colspan=2>
							<html:text styleClass="formFieldSized" size="30" styleId="irbID" property="irbID" readonly="<%=readOnlyValue%>" />
						</td>
					</tr>

<!-- startdate -->						
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel">
							<label for="startDate">
								<bean:message key="distributionprotocol.startdate" />
							</label>
						</td>
			
						<td class="formField" colspan=2>
							<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
						 	<html:text styleClass="formDateSized" size="35" styleId="startDate" property="startDate" />
							<a href="javascript:show_calendar('distributionProtocolForm.startDate',null,null,'MM-DD-YYYY');">
								<img src="images\calendar.gif" width=24 height=22 border=0>
							</a>
						</td>
					</tr>

<!-- enddate -->						
					<tr>
						<td class="formRequiredNotice" width="5">&nbsp;</td>
						<td class="formLabel">
							<label for="endDate">
								<bean:message key="distributionprotocol.enddate" />
							</label>
						</td>
			
						 <td class="formField" colspan=2>
						 <div id="enddateDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
						 <html:text styleClass="formDateSized" size="35" styleId="endDate" property="endDate" />
							<a href="javascript:show_calendar('distributionProtocolForm.endDate',null,null,'MM-DD-YYYY');">
								<img src="images\calendar.gif" width=24 height=22 border=0>
							</a>
						 </td>
					</tr>

<!-- no of participants -->						
					<tr>
						<td class="formRequiredNotice" width="5">&nbsp;</td>
						<td class="formLabel">
							<label for="enrollment">
								<bean:message key="distributionprotocol.participants" />
							</label>
						</td>
						<td class="formField" colspan=2>
							<html:text styleClass="formFieldSized" size="30" styleId="enrollment" property="enrollment" readonly="<%=readOnlyValue%>" />
						</td>
					</tr>

<!-- descriptionurl -->						
					<tr>
						<td class="formRequiredNotice" width="5">&nbsp;</td>
						<td class="formLabel">
							<label for="descriptionURL">
								<bean:message key="distributionprotocol.descriptionurl" />
							</label>
						</td>
						<td class="formField" colspan=2>
							<html:text styleClass="formFieldSized" size="30" styleId="descriptionURL" property="descriptionURL" readonly="<%=readOnlyValue%>" />
						</td>
					</tr>



<!-- activitystatus -->	
					<logic:equal name="<%=Constants.OPERATION%>" value="<%=Constants.EDIT%>">
					<tr>
						<td class="formRequiredNotice" width="5">*</td>
						<td class="formRequiredLabel" >
							<label for="activityStatus">
								<bean:message key="site.activityStatus" />
							</label>
						</td>
						<td class="formField">
							<html:select property="activityStatus" styleClass="formFieldSized10" styleId="activityStatus" size="1">
								<html:options name="<%=Constants.ACTIVITYSTATUSLIST%>" labelName="<%=Constants.ACTIVITYSTATUSLIST%>" />
							</html:select>
						</td>
					</tr>
					</logic:equal>
							
				</table> 	<!-- table 4 end -->
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr> <!-- SEPARATOR -->
</table>


<table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="97%">
	<tr><td>
		<table summary="" cellpadding="3" cellspacing="0" border="0" width="100%">
			<tr>
				<td class="formTitle">
						<b>SPECIMEN REQUIREMENTS</b>
						<html:hidden property="counter"/>
				</td>
				<td align="right" class="formTitle">		
						<html:button property="addDistributionProtocolEvents" styleClass="actionButton" onclick="insRow('SpecimenRequirementData')">Add More</html:button>
				</td> 
				<td class="formTitle" align="Right">
					<html:button property="deleteValue" styleClass="actionButton" onclick="deleteChecked('SpecimenRequirementData','/catissuecore/DistributionProtocol.do?operation=add&pageOf=pageOfDistributionProtocol&status=true')">
					<bean:message key="buttons.delete"/>
					</html:button>
				</td>
			</tr>
		</table>	
			<!-- SUB TITLES -->
	<tr><td>
			<table summary="" cellpadding="3" cellspacing="0" border="0" width="100%">
			<tbody id="SpecimenRequirementData">			
			<TR> 
				<td class="formLeftSubTitle" ><!-- width="14" -->
					<bean:message key="distributionprotocol.specimennumber" />
		        </td>
				
				<td class="formLeftSubTitle">* <!--  width="182" -->
					<bean:message key="distributionprotocol.specimenclass" />
		        </td>
		        
		        <td class="formLeftSubTitle">* <!-- width="180" -->
			        <bean:message key="distributionprotocol.specimentype" />
		        </td>
		        
		        <td class="formLeftSubTitle">* <!--  width="211" -->
		        	<bean:message key="distributionprotocol.specimensite" />
			    </td>
		        
		        <td class="formLeftSubTitle">* <!--  width="208" -->
			        <bean:message key="distributionprotocol.pathologystatus" />
			    </td>
			    
			    <td class="formLeftSubTitle">* <!--  width="117" -->
			    	<b><bean:message key="distributionprotocol.quantity" /></b>
		        </td>
		        <td class="formRightSubTableTitle">
							<label for="delete" align="center">
								<bean:message key="distributionprotocol.delete" />
							</label>
						</td>
			</tr>	

			<%
				int maxcount=1;
				for(int counter=noOfRows;counter>=1;counter--)
				{		
					String objname = "value(SpecimenRequirement:" + counter + "_specimenClass)";
					String srCommonName = "SpecimenRequirement:" + counter;
					String srKeyName = srCommonName + "_specimenClass";
					String srSubTypeKeyName = srCommonName + "_specimenType";
					
					String objunit = "value(SpecimenRequirement:"+ counter +"_unitspan)";
					String identifier = "value(SpecimenRequirement:"+ counter +"_systemIdentifier)";
					String check = "chk_"+counter;
			%>
			<TR> 
				<td class="tabrightmostcell">
					<%=counter%>
					<html:hidden property="<%=identifier%>" />				
		        </td>
			<%
				String functionName ="changeUnit('" + objname + "',' " + objunit + "')"; 
				String subTypeFunctionName ="onSubTypeChangeUnit('" + objname + "',this,' " + objunit + "')"; 
			%>	
				<td class="formField">
					<html:select property="<%=objname%>" styleClass="formFieldSized10" styleId="<%=objname%>" size="1" onchange="<%=functionName%>" >
						<html:options collection="<%=Constants.SPECIMEN_CLASS_LIST%>" labelProperty="name" property="value"/>
					</html:select>
		        </td>
		        
		        <td class="formField">
					<%
					
						String classValue = (String)form.getValue(srKeyName);
						
						specimenTypeList = (List)specimenTypeMap.get(classValue);
								
						if(specimenTypeList == null)
						{
							specimenTypeList = new ArrayList();
							specimenTypeList.add(new NameValueBean(Constants.SELECT_OPTION,"-1"));
						}
						pageContext.setAttribute(Constants.SPECIMEN_TYPE_LIST, specimenTypeList);						
						String tmpSpecimenClass = objname;
						
						objname="";
						objname = "value(SpecimenRequirement:" + counter + "_specimenType)";
						
						
						
					%>
					<html:select property="<%=objname%>" styleClass="formFieldSized10" styleId="<%=objname%>" size="1"  onchange="<%=subTypeFunctionName%>" >
						<html:options collection="<%=Constants.SPECIMEN_TYPE_LIST%>" labelProperty="name" property="value"/>
					</html:select>
		        </td>
		        
		        <td class="formField">
					<%
						objname="";
						objname = "value(SpecimenRequirement:" + counter + "_tissueSite)";
					%>
					<html:select property="<%=objname%>" styleClass="formFieldSized35" styleId="<%=objname%>" size="1" >
						<html:options collection="<%=Constants.TISSUE_SITE_LIST%>" labelProperty="name" property="value"/>
					</html:select>
					<%
						String url = "ShowFramedPage.do?pageOf=pageOfTissueSite&propertyName="+objname;			
					%>
				    <a href="#" onclick="javascript:NewWindow('<%=url%>','name','250','330','no');return false">
						<img src="images\Tree.gif" border="0" width="26" height="22">
					</a>
			    </td>
		        
		        <td class="formField">
					<%
						objname="";
						objname = "value(SpecimenRequirement:" + counter + "_pathologyStatus)";
					%>
					<html:select property="<%=objname%>" styleClass="formFieldSized10" styleId="<%=objname%>" size="1" >
						<html:options collection="<%=Constants.PATHOLOGICAL_STATUS_LIST%>" labelProperty="name" property="value"/>
					</html:select>
			    </td>
			    
			    <td class="formField">
			    	<%
						objname="";
						objname = "value(SpecimenRequirement:"+ counter +"_quantityIn)";
						String typeclassValue = (String)form.getValue(srSubTypeKeyName);
								 String strHiddenUnitValue = "" + changeUnit(classValue,typeclassValue);
								 
						%>

		        		
			    
					<html:text styleClass="formFieldSized5" 
								styleId="<%=objname%>" 
								property="<%=objname%>"
								readonly="<%=readOnlyValue%>" />
						&nbsp;
						<span id=' <%= objunit %>'>
							<%=strHiddenUnitValue%>
						</span>
		        </td>
		        <%
							String key = "SpecimenRequirement:"+ counter +"_systemIdentifier";
							boolean bool = Utility.isPersistedValue(map,key);
							String condition = "";
							if(bool)
								condition = "disabled='disabled'";

						%>
						<td class="formField" width="5">
							<input type=checkbox name="<%=check %>" id="<%=check %>" <%=condition%>>		
						</td>
			</tr>	
							
			<%
				} // for 
			%>
			
			</tbody>
			<!-- SUB TITLES END -->		 
		</table>
	</td></tr>
</table>


<table width="97%">		
	<!-- to keep -->
		<tr>
			<td align="right" colspan="3">
				<!-- action buttons begins -->
				<!-- table 6 -->
				<table cellpadding="4" cellspacing="0" border="0">
					<tr>
						<td>
							<html:submit styleClass="actionButton">
								<bean:message  key="buttons.submit" />
							</html:submit>
						</td>
						<td>
							<html:reset styleClass="actionButton" >
								<bean:message  key="buttons.reset" />
							</html:reset>
						</td>
					</tr>
				</table>
				<!-- table 6 end -->
				<!-- action buttons end -->
			</td>
		</tr>
</table>
</html:form>
</body>