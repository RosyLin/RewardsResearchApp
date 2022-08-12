import React, {Component} from 'react';
import styled from 'styled-components';
import Table from 'react-bootstrap/Table';

const TableS = styled(Table) `
    border-collapse: inherit;
	width: 330px;    
    background-color: #e5f1ff;
    padding: 15px;
    color: navy;
`;

const THeader = styled.th`
    display: contents;
    line-height: 1.7;
`;

const TDLeft = styled.td`
    text-align: left;
    font-size: 12px;
    font-family: monospace;
    line-height: 0.9;
`;

const THLeft = styled.td`
    text-align: left;
    font-size: 12px;
    font-family: monospace;
    line-height: 0.9;
    padding-top: 10px;
    text-decoration: underline;
    font-weight: bold;
`;

const THRight = styled.th`
    text-align: right;
    font-size: 12px;
    font-family: monospace;
    line-height: 0.9;
`;

const TBody = styled.tbody`
    display: contents;
`;

const Div = styled.tr`
    line-height: 0.5;
`;

class PurchaseHistory extends Component
{
    constructor(props){
        super(props);

        this.state = {
            ...props.purchaseDetails,
        };

    }

    renderItems = () => { //Dyanmically create table from data
        //console.log (data.properties.customers.length);
        var items = [];
        console.log(this.state)
        for (var i =0; i < this.state.purchaseHistoryResponse.items.length; i++)
        { 
            items [i] =
            (
            <TBody>
                <tr>
                    <THLeft >{this.state.purchaseHistoryResponse.items[i].desc}</THLeft>
                </tr>
               
                <tr>
                    <TDLeft> Product ID/SKU: </TDLeft>
                    <THRight>{this.state.purchaseHistoryResponse.items[i].sku}</THRight>
                </tr>
               
                <tr>
                    <TDLeft >{this.state.purchaseHistoryResponse.items[i].id} {this.state.purchaseHistoryResponse.items[i].attributes[0].desc} {this.state.purchaseHistoryResponse.items[i].attributes[1].desc}</TDLeft>
                </tr>
                
                <tr>
                    <TDLeft >{this.state.purchaseHistoryResponse.items[i].quantity} x ${this.state.purchaseHistoryResponse.items[i].prices.price}</TDLeft>
                    <THRight> total:${this.state.purchaseHistoryResponse.items[i].prices.price}</THRight>
                </tr>

                <tr>
                    <TDLeft > Bean Bucks Applied: </TDLeft>
                    <THRight> {this.state.purchaseHistoryResponse.items[i].prices.rewardsAmount}</THRight>
                </tr>
            
            </TBody>
            );
        }
        return items;
    }

    render = () => {
        return (
                <TableS>
                    <THeader> TRANSACTION DETAILS </THeader>
                        <tr>
                            <TDLeft >Transaction Type: </TDLeft>
                            <THRight>{this.state.purchaseHistoryResponse.type}</THRight>
                        </tr>
                        <tr>
                            <TDLeft >Customer Name: </TDLeft>
                            <THRight>{this.state.purchaseHistoryResponse.buyer.customerName.firstGivenName} {this.state.purchaseHistoryResponse.buyer.customerName.surName}</THRight>
                        </tr>
                        <tr>
                            <TDLeft >Customer ID: </TDLeft>
                            <THRight>{this.state.purchaseHistoryResponse.buyer.customerId}</THRight>
                        </tr>
                        <Div>----------------------------</Div>

                        <TBody>
                            {this.renderItems()}
                        </TBody>

                        <Div>----------------------------</Div>
                        <tr>
                            <TDLeft> Order Total: </TDLeft>
                            <THRight> ${this.state.purchaseHistoryResponse.financials.currentMerchandiseTotal} </THRight>
                        </tr>
                        <tr>
                            <TDLeft> Total Bean Bucks Used: </TDLeft>
                            <THRight> {this.state.purchaseHistoryResponse.financials.rewardsAppliedTotalAmount} </THRight>
                        </tr>

                        <tr>
                            <TDLeft > </TDLeft>
                        </tr>            
                </TableS>
        )
    }
}

export default PurchaseHistory;


