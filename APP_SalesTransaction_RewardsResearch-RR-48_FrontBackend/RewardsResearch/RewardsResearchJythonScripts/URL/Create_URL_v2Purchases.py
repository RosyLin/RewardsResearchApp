#WASGEN Template - ActivationSpec
#############################################################
# 2.0 - 04/27/2012 - Brian Bouchard
#	- complete rewrite of template for easier maintenance
#############################################################

########################
# USER PROVIDED DATA
########################
urlName="v2Purchases"
jndiName="url/v2Purchases"
specificationUrl="https://test.api.llbean.com/v2/purchases"

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
