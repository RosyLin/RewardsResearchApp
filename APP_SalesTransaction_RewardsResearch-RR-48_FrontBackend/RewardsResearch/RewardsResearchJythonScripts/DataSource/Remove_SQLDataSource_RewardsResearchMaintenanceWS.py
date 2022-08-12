#WASGEN Template - Remove SQL Server DataSources
#############################################################
#1.0.0 - 03/27/2018 - Thomas Gould  - initial create
#############################################################
import os

########################
# USER PROVIDED DATA
########################
DatasourceName="RewardsResearchEnterpriseServices_SQL_XA"
xa = "y"

if (xa == 'y'):
   JDBCProvider="Microsoft SQL Server JDBC Driver (XA)"
else:
   JDBCProvider="Microsoft SQL Server JDBC Driver"

node = AdminControl.getNode()
cell = AdminControl.getCell()

sec = AdminConfig.getid('/Cell:' + cell + '/JDBCProvider:'+ JDBCProvider + '/DataSource:'+ DatasourceName +'/' )

print sec

AdminConfig.remove(sec)

print "Success! Datasource removed. Saving the configuration"
AdminConfig.save()
