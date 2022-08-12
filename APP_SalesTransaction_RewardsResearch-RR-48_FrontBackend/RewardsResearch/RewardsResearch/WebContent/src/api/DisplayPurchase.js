import React, { Component } from 'react';
import getPurchase from './getPurchase';
import PurchaseHistory from '../module/PurchaseHistory';
import AlertShow from '../module/AlertShow';

class DisplayPurchase extends Component {

    constructor(props) {

        super(props);

        this.state = {
            purchaseToSearch : '',
            searchDisabled : true,
            results : null
        };

        this.createPurchase();

    }

    createPurchase = async () => {
        try{
            let purchaseDetails = await getPurchase('010011542174');
            if(purchaseDetails.error) {
                this.displayMessage('warning', 'Unable To Find Purchase History.')
            }
            else {
                await this.displayResults(purchaseDetails);
            }
        }
        catch(err) {
            this.displayMessage('error', 'Issue displaying Customer info', 'Please refresh the page and try again. If the issue continues, contact Client Support.');
        }

        this.setState({
            purchaseToSearch : ''
        });
    }

    displayResults = async (purchaseDetails) => {
        let results = (
            <PurchaseHistory purchaseDetails={purchaseDetails}/>
        );
        
        this.setState({
            results : results
        });
    }

    displayMessage = async (variant, messageBody) => {
        let message = (
            <AlertShow 
                variant={variant} 
                messageBody={messageBody}
            />
        );
        this.setState({
            results : message
        });
    }

    render = () => {
        return (
            <div>
                {this.state.results}

            </div>

        );
    }


}
export default DisplayPurchase;