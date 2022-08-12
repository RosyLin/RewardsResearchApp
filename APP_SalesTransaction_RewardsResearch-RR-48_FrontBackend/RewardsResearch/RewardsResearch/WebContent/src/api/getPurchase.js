import axios from "axios";

/**
 * Use the Tag Printing service tag-details call to get the price of a sku at the given store
 * @param {*} orderID
 */

//const orderID = "010011542174";
 const getTransactionUrl = '/RewardsResearch/api/transaction?';

 export default async function getByOrderID(orderID) {
    let purchaseDetails = {};

    await axios.get(`${getTransactionUrl}orderId=${orderID}`)
    .then((response) => {
        purchaseDetails = response.data;

    }).catch ((error)=> {
        console.log(error);
        purchaseDetails = {
            error: true
        }
    });

    return purchaseDetails;

 }