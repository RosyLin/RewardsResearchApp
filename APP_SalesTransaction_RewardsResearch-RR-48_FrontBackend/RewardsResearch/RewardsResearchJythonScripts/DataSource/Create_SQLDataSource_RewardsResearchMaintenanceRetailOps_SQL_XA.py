#WASGEN Template - SQL Server DataSources
#############################################################
#1.0.0 - 10/16/2017 - Thomas Gould  - initial create
#############################################################
import os

########################
# USER PROVIDED DATA
########################
xa = "y"
UserID = "svc-sdawsd"
dbname = "RetailOps"
DB_ServerName = "llb-sqlds01"
PortNumber = "1433"
DataSourceName="RewardsResearchRetailOps_SQL_XA"
JNDIName ="jdbc/RewardsResearchRetailOps_SQL_XA"


node = AdminControl.getNode()
cell = AdminControl.getCell()

if xa == "y":
	dataSourceFactoryName = "Microsoft SQL Server JDBC Driver - XA DataSource"
	dataSourceProvider = "Microsoft SQL Server JDBC Driver (XA)"
else: 
	dataSourceFactoryName = "Microsoft SQL Server JDBC Driver - DataSource"
	dataSourceProvider = "Microsoft SQL Server JDBC Driver"

########################
#Since we will only run this on Windows desktops, we can always scope at the cell level
########################
newjdbc = AdminConfig.getid('/Cell:' + cell + '/JDBCProvider:' + dataSourceProvider +'/')


print " "
print "   Creating Datasource..."
print " "
	
authDataAlias = UserID
userAttr = ['authDataAlias', 'authDataAlias']
attrs = []
attrs.append(["name", DataSourceName])
attrs.append(["description", "Script Generated "+DataSourceName])
attrs.append(["jndiName", JNDIName])
attrs.append(["statementCacheSize", "30"])
attrs.append(["authMechanismPreference", "BASIC_PASSWORD"])
attrs.append(["authDataAlias", authDataAlias])

if xa == "y":
	attrs.append(["xaRecoveryAuthAlias", authDataAlias]) 

template = AdminConfig.listTemplates("DataSource", dataSourceFactoryName )
        
dataSource = AdminConfig.createUsingTemplate("DataSource", newjdbc, attrs, template )


AdminConfig.save()

#--------------------------------------------------------------
# Clean up unused properties of the DataSource.
#--------------------------------------------------------------
psAttr = ["propertySet", []]
attrs = []
attrs.append(psAttr)
AdminConfig.modify(dataSource, attrs)
 
#--------------------------------------------------------------
# Add desired custom properties to the DataSource.
#--------------------------------------------------------------
 
dbnameAttr = [["name", "databaseName"], ["value", dbname], ["type", "java.lang.String"]]
svrnameAttr = [["name", "serverName"], ["value", DB_ServerName], ["type", "java.lang.String"]] 
portAttr = [["name", "portNumber"], ["value", PortNumber], ["type", "java.lang.String"]]
drivertypeAttr = [["name", "driverType"], ["value", "4"], ["type", "java.lang.Integer"]]

newsprops = []
newsprops.append(dbnameAttr)
newsprops.append(svrnameAttr)
newsprops.append(portAttr)
newsprops.append(drivertypeAttr)

psAttr = ["propertySet", [["resourceProperties", newsprops]]]
attrs = [psAttr]
AdminConfig.modify(dataSource, attrs)

AdminConfig.modify(dataSource, '[[connectionPool [[minConnections 0]]]]')

# print AdminConfig.showall(dataSource)

#--------------------------------------------------------------
# Save the new datasource
#-------------------------------------------------------------- 

print "Success! DataSource Created! Saving the configuration"
AdminConfig.save( )
