USE [Payment]
GO
/****** Object:  StoredProcedure [dbo].[PMTR0086]    Script Date: 7/21/2022 2:43:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*
*************************************************************************************
 [OBJECT NAME]: dbo.PMTR0087
 [DESCRIPTION]: Select Authorization History For Given Loyalty ID
 [NOTES]:
	 ------------------------------------------------------------------------ 
	-- SQL Stored Procedure
	-- Long Name:  STORED PROCEDURE TO SELECT AUTHORIZATION HISTORY FOR GIVEN LOYALTY ID
	-- Result Set Range: ALL
	 ------------------------------------------------------------------------

 [PARAMETERS]: 
  @loyalty_id CHAR(10) DEFAULT: NULL <description>



 [MODIFICATION HISTORY]:
   Date			Author					Comment
 -----------	----------------------	-----------------------------------------
 21 JUL 2022    Lin, Rosy               Initial release
*************************************************************************************
*/

ALTER PROCEDURE [dbo].[PMTR0087] ( 
  @loyalty_id char(10)
  )
AS

-- declare @loyalty_id varchar(255) = '9280011479';

	select AUTH_TOKEN_ID, AUTH_RQST_TS, ORD_ID, TRN_TS, LL_STORE_ID, TRN_ID, RGST_ID, PMD_DVC_ID, AUTH_FUNC_CD, AUTH_AMT, TRN_TOT_AMT, AUTH_SYS_IND from
	[Payment].[dbo].[AUTH_RQST_HIST] 
	where  
	PMD_DVC_ID=@loyalty_id 
	order by auth_rqst_ts asc 


	select arh.AUTH_TOKEN_ID, arh.PMD_AUTH_RSP_TS, arh.PMD_AUTH_AMT, arh.PMD_AUTH_REAS_CD, arh.AUTH_RSP_TYP_CD,
	arh.ISD_RSP_CD, arh.REMAIN_BAL_AMT, arp.PMT_MTHD_ID, arp.STRAT_AUTH_RSP_ID, arp.RSP_TXT_DSPL_DESC, 
	arp.RSP_DESC, arp.AUTH_DCLN_TYP, arp.AUTH_RSP_TYP_ID, arp.ORD_AUTH_RTG_IND, arp.AUD_PGM_ID, arp.AUD_OPID,arp.AUD_TS
	from [Payment].[dbo].[AUTH_RSP_HIST] as arh
	inner join [Payment].[dbo].[AUTH_RSP_PMD] as arp on arh.PMD_AUTH_REAS_CD = arp.STRAT_AUTH_RSP_ID
	where auth_token_id in
	(
		select auth_token_id from
		[Payment].[dbo].[AUTH_RQST_HIST]
		where 
		PMD_DVC_ID=@loyalty_id
	)