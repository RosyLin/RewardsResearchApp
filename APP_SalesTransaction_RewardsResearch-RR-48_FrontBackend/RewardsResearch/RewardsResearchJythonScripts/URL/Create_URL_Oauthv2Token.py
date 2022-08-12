#WASGEN Template - ActivationSpec
#############################################################
# 2.0 - 04/27/2012 - Brian Bouchard
#	- complete rewrite of template for easier maintenance
#############################################################

########################
# USER PROVIDED DATA
########################
urlName="Oauthv2Token"
jndiName="url/Oauthv2Token"
specificationUrl="https://test.api.llbean.com/oauth/v2/token?"

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
