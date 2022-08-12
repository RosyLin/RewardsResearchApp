#WASGEN Template - ActivationSpec
#############################################################
# 2.0 - 04/27/2012 - Brian Bouchard
#	- complete rewrite of template for easier maintenance
#############################################################

########################
# USER PROVIDED DATA
########################
urlName="v1PortalSession"
jndiName="url/v1PortalSession"
specificationUrl="http://mw-itinfrastructuresvcstest-lb.llbean.com/portal-service/v1.0/llb-sessions/"

########################
# CREATE RESOURCE
########################
cell = AdminControl.getCell()
newurlp = AdminConfig.getid("/Cell:" + cell + "/URLProvider:/")

name = ['name', urlName]
spec = ['spec', specificationUrl]
jndiName = ['jndiName',jndiName]
urlAttrs = [name, jndiName, spec]

AdminConfig.create('URL', newurlp, urlAttrs)
print "Success! URL Created. Saving the configuration"
AdminConfig.save()
