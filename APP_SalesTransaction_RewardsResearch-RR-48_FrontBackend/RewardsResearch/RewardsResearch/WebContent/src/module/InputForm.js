import React, { Component } from 'react';
import  {useState} from 'react';
import {InputGroup} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Button from './Button';
import getRewards from '../api/getRewards';
import RewardsInfo from './RewardsInfo';
import AlertShow from './AlertShow';
import styled from 'styled-components';

// 00357935531

const MyDiv = styled.div`
    border: #0f674e solid;
    border-width: 5px;
    align-item: center;
    border-radius: 3px;
    padding: 30px;
    display: flex;
    min-width: 500px;
    margin: 20px;
    background-color: #117458;
`
const MyDiv2 = styled.div`
    align-item: center;
`

const MyInput = styled(InputGroup)`
    margin: 8px;

`
const Row = styled.tr`
    text-align: center;

`

class InputForm extends Component {

    constructor(props) {

        super(props);

        this.state = {
            custToSearch : '',
            searchDisabled : true,
            results : null
        };

    }


    submit = async (event) => {
        if(event !== undefined){
            event.preventDefault();
        }

        await this.setState({
            results : null
        })

        if(this.state.custToSearch.length !== 11) {
            //cust validation failed
            this.displayMessage('warning', 'Invalid Customer ID! Custtomer ID length must be 11 characters. Please try again.');
        }
        else {
            try {
                let custDetails = await getRewards(this.state.custToSearch);
                if(custDetails.error) {
                    this.displayMessage('warning', 'Unable To Find Customer.')
                }
                else {
                    await this.displayResults(custDetails);
                }
            }
            catch(err) {
                this.displayMessage('error', 'Issue displaying Customer info', 'Please refresh the page and try again. If the issue continues, contact Client Support.');
            }
        }

        this.setState({
            custToSearch : ''
        });

    }

    displayResults = async (custDetails) => {
        let results = (
            <RewardsInfo custDetails={custDetails}/>
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

    handleCustChange = async (event) => {
        this.setState({
            custToSearch : event.target.value
        });
    }

    render = () => {


        return (
        <MyDiv2>
            <Row>
                <p> Welcome! Please enter CUSTOMER ID below to look up rewards details:</p>
            </Row>
            
            <form
                onSubmit = {this.submit}
            >
            {/* <StoreDropDown setSearchDisabled={this.setSearchDisabled} setStoreSelectionKey={this.setStoreSelectionKey}/> */}
                <MyDiv>
                    <div>
                       <MyInput className="mb-3">
                            <InputGroup.Text>Customer ID: </InputGroup.Text>
                            <input 
                                autoFocus
                                className='menu-cust'
                                type='text' 
                                placeholder="ID #"
                                aria-label="customerID"
                                name="inputtedCustID"
                                value={this.state.custToSearch}
                                ref={(input) => { this.custToSearch = input; }}
                                onChange = {this.handleCustChange}
                            />
                        </MyInput>
                    </div>

                    <Button
                        type = 'submit'
                        className = 'menu-btn-submit'
                        variant = "primary"
                    >SUBMIT</Button>

                </MyDiv>
            </form>
                {this.state.results}
        </MyDiv2>
        );  
    }

}





export default InputForm;