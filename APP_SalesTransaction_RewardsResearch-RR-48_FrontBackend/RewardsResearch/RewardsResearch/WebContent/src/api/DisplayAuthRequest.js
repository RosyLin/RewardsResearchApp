import React, { Component } from 'react';
import AuthRequest from '../module/AuthRequest';
import AlertShow from '../module/AlertShow';
import getAuthRequest from './getAuthRequest';

class DisplayAuthRequest extends Component {

    constructor(props) {

        super(props);

        this.state = {
            AuthRequestToSearch : props.id,
            results : null
        };
        this.createAuthRequest();

    }

    createAuthRequest = async () => {
        try {
            console.log("inside Create Auth Request 001");
            let authRequest = await getAuthRequest(this.state.AuthRequestToSearch);
            console.log("inside Create Auth Request 002 and AuthRequest is: " + authRequest);
            if(authRequest.error) {
                this.displayMessage('warning', 'Unable To Find Customer.')
            }
            else {
                console.log(authRequest)
                console.log(JSON.stringify(authRequest))
                await this.displayResults(authRequest);
            }
        }
        catch(err) {
           this.displayMessage('error', 'Issue displaying Customer info', 'Please refresh the page and try again. If the issue continues, contact Client Support.');
        }
        console.log("inside Create Auth Request 003")
        this.setState({
            AuthRequestToSearch : ''
        });

    }

    displayResults = async (authRequest) => {
        console.log("inside DisplayResults --1");
        let results = (
            <AuthRequest authRequest={authRequest}/>
        );
        console.log("inside DisplayResults --2");
        this.setState({
            results : results
        });
        console.log("inside DisplayResults --3");
    }

    displayMessage = async (variant, messageBody) => {
        console.log("inside DisplayMMMessage --1");
        let message = (
            <AlertShow 
                variant={variant} 
                messageBody={messageBody}
            />
        );
        console.log("inside DisplayMMMessage --2");
        this.setState({
            results : message
        });
        console.log("inside DisplayMMMessage --3");
    }

    render = () => {
        return (
            <div>
                console.log("RENDER 1");
                {this.state.results}
                console.log("RENDER 2");
            </div>

        );
    }


}
export default DisplayAuthRequest;