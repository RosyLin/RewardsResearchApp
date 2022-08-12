import React, {Component} from 'react';
import styled from 'styled-components';
import Table from 'react-bootstrap/Table';
import DisplayAuthRequest from '../api/DisplayAuthRequest';

const TR = styled.tr`
    cursor: pointer;
    background-color: white;
    :hover {
        background-color: rgba(222,227,232,0.6);
    }
`; 

const TableS = styled(Table) `
    margin: 20px;
`;

const THHeader = styled.th`
    background-color: #dee3e8;
    padding: .6rem;
    color: #0C5641;
    border: solid #0C5641 1.5px;
    font-size: 14px;
`;

const TD = styled.td`
    text-align: center;
    padding: .6rem;
    background-color: white;
    color: #0C5641;
    border: solid #0C5641 1.5px;
    font-size: 14px;
    :hover {
        text-decoration: underline;
    }
`;

class RewardsInfo extends Component
{

    constructor(props){
        super(props);

        this.state = {
            ...props.custDetails,
            DisplayAuthRequest: null
        };

    }

    clickAccountId = (id) => {
        console.log("inside click account ID");
        console.log("the id is: " + id);
        let DisplayAuthRequest = (
            <DisplayAuthRequest id = {id}/>
        );
        console.log("display auth request is: ", DisplayAuthRequest);   
        console.log("now setting the state..")
        this.setState({
            DisplayAuthRequest : DisplayAuthRequest
        });
        console.log("finished setting the state..")
    }

    renderRows = () => { 
        var rows = [];
        var id;
        for (var i =0; i < this.state.rewardsBalance.rewardsAccounts.length; i++)
        { 
        id = this.state.rewardsBalance.rewardsAccounts[i].accountId;
            rows[i] =
            (
            <TR onClick={() => {
                console.log(this.state.rewardsBalance);
                this.clickAccountId(id);
                console.log("render rows --1");         
                }}>

                <TD>{this.state.rewardsBalance.rewardsAccounts[i].accountId}</TD>
                <TD>{this.state.rewardsBalance.rewardsAccounts[i].rewardsBalanceAmount}</TD>
                <TD>{this.state.rewardsBalance.rewardsAccounts[i].rewardsAvailableFlag}</TD>
                <TD>{this.state.rewardsBalance.rewardsAccounts[i].actionCode}</TD>
                <TD>{this.state.rewardsBalance.rewardsAccounts[i].actionStatus}</TD>
                <TD>{this.state.rewardsBalance.rewardsTotalAmount}</TD>
            </TR>
            );
        }   
        return rows;
    }

    render = () => {
        return (
            <div>
            <TableS>
                <TR>
                    <THHeader >Account ID</THHeader>
                    <THHeader >Rewards Balance</THHeader>
                    <THHeader >Rewards Available Flag</THHeader>
                    <THHeader >Action Code</THHeader>
                    <THHeader >Action Status</THHeader>
                    <THHeader >Total Rewards</THHeader>
                </TR>

                <tbody>
                  {this.renderRows()}
                </tbody>
            </TableS> 
            {this.state.DisplayAuthRequest} 
            </div>
        );
    }
}

export default RewardsInfo;


