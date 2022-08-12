import axios from "axios";

/**
 * Use the Tag Printing service tag-details call to get the price of a sku at the given store
 * @param {*} customerID
 */


 const getRewardsUrl = '/RewardsResearch/api/rewards?';
 export default async function getRewards(customerID) {

    let custDetails = {};

    await axios.get(`${getRewardsUrl}customerId=${customerID}`)
    .then((response) => {
        custDetails = response.data;
    }).catch ((error)=> {

        console.log(error);
        custDetails = {
            error: true
        }
    });

    return custDetails;


 








}