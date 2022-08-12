import axios from "axios";

/**
 * Use the Tag Printing service tag-details call to get the price of a sku at the given store
 * @param {*} loyaltyID
 */


 const getAuthorizationsUrl = '/RewardsResearch/api/authorizations?';
 export default async function getAuthRequest(loyaltyID) {

    let authRequest = {};

    await axios.get(`${getAuthorizationsUrl}customerId=${loyaltyID}`)
    .then((response) => {
        authRequest = response.data;
    }).catch ((error)=> {

        console.log(error);
        authRequest = {
            error: true
        }
    });

    return authRequest;


 








}