import React, {Component} from 'react';
import styled from 'styled-components';
import Table from 'react-bootstrap/Table'

const custSearchResultsJSON = 
{
    "properties": {
        "customers": [
            {
                "customerId": "00823928033",
                "firstName": "A",
                "lastName": "SMITH",
                "gender": "U",
                "addressId": "60065735380",
                "addressLine1": "16 BRIGHAM ST",
                "city": "S PORTLAND",
                "state": "ME",
                "phone1": "(276)206-9011",
                "zipCode": "041063406",
                "country": "USA"
            },
            {
                "customerId": "00213171937",
                "firstName": "ARNIE",
                "lastName": "SMITH",
                "gender": "M",
                "addressId": "71863180833",
                "addressLine1": "123 MAIN ST",
                "city": "SOUTH PORTLAND",
                "state": "ME",
                "zipCode": "041062621",
                "country": "USA",
                "email1": "ARNIE@SMITH.COM",
                "email1OptinFlag": true,
                "email1ShipNotifyFlag": true,
                "email1AddressId": "210976448",
                "email2": "ARNIE@SMITH.COM",
                "email2OptinFlag": true,
                "email2ShipNotifyFlag": true,
                "email2AddressId": "210976448",
                "phone1": "1-(207)552-2342",
                "phone1DayNightInd": "D",
                "phone1Type": "H"
            },
            {
                "customerId": "00914484241",
                "firstName": "CATHRYN",
                "lastName": "SMITH",
                "gender": "F",
                "addressId": "57179335042",
                "addressLine1": "35 KESWICK RD",
                "city": "S PORTLAND",
                "state": "ME",
                "zipCode": "041065335",
                "country": "USA"
            },
            {
                "customerId": "00327973404",
                "firstName": "CHRIS",
                "middleName": "DANGER",
                "lastName": "SMITH",
                "gender": "U",
                "addressId": "60815981235",
                "addressLine1": "789 MAIN ST",
                "city": "FREEPORT",
                "state": "ME",
                "zipCode": "04106",
                "country": "USA",
                "email1": "MATT62@GMAIL.COM",
                "email1OptinFlag": true,
                "email1ShipNotifyFlag": true,
                "email1AddressId": "304089469",
                "email2": "MATT62@GMAIL.COM",
                "email2OptinFlag": true,
                "email2ShipNotifyFlag": true,
                "email2AddressId": "304089469",
                "phone1": "(123)456-1111",
                "phone1DayNightInd": "B",
                "phone1Type": "H"
            },
            {
                "customerId": "00951536714",
                "firstName": "CHUCK",
                "lastName": "SMITH",
                "gender": "U",
                "addressId": "00670465240",
                "addressLine1": "5 MAIN ST",
                "city": "SOUTH PORTLAND",
                "state": "ME",
                "zipCode": "041062617",
                "country": "USA",
                "email1": "CHUCK63@LLBEAN.COM",
                "email1OptinFlag": true,
                "email1ShipNotifyFlag": true,
                "email1AddressId": "787984483",
                "email2": "CHUCK63@LLBEAN.COM",
                "email2OptinFlag": true,
                "email2ShipNotifyFlag": true,
                "email2AddressId": "787984483",
                "phone1": "(276)206-9011",
                "phone1DayNightInd": "D",
                "phone1Type": "H"
            }
        ]
    },
    "links": [
        {
            "rel": "self",
            "href": "https://v1/customers"
        }
    ],
    "actions": []
}

const TableS = styled(Table) `
    margin: 20px;
    border: #0C5641;
    background-color: #dee3e8;
`;

const THHeader = styled.th`
    padding: .6rem;
    color: #0C5641;
    border: solid #0C5641 1.5px;
    font-size: 14px;
    background-color: #dee3e8;
`;

const TD = styled.td`
	word-break: break-word;
	font-size: 14px;
    text-align: center;
    padding: .5rem;
`;

const TR = styled.tr`
    cursor: pointer;
    background-color: #0a5740;
    :hover {
        background-color: #117458;
    }
`;

const TLink = styled.td`
    word-break: break-word;
    font-size: 14px;
    text-align: center;
    padding: .4rem;
    cursor: pointer;
    text-decoration: underline;
`;


class CustomerSearch extends Component
{
    renderRows = () => { //Dyanmically create table from custSearchResultsJSON
        //console.log (custSearchResultsJSON.properties.customers.length);
        var rows = [];
        for (var i =0; i < custSearchResultsJSON.properties.customers.length; i++)
        { 
        rows [i] =
        (<TR>
            <TLink>{custSearchResultsJSON.properties.customers[i].customerId}</TLink>
            <TD>{custSearchResultsJSON.properties.customers[i].lastName}</TD>
            <TD>{custSearchResultsJSON.properties.customers[i].addressLine1}</TD>
            <TD>{custSearchResultsJSON.properties.customers[i].phone1}</TD>
            <TD>{custSearchResultsJSON.properties.customers[i].email2}</TD>
        </TR>);
        }
        
        return rows;
    }

    render = () => {
        return (
                <TableS>
                    <thead>
                        <tr>
                            <THHeader >Customer ID</THHeader>
                            <THHeader >Customer Name</THHeader>
                            <THHeader >Address</THHeader>
                            <THHeader >Phone #</THHeader>
                            <THHeader >Email</THHeader>
                        </tr>
                    </thead>

                    <tbody>
                      {this.renderRows()}
                    </tbody>
                </TableS>
        )
    }
}

export default CustomerSearch;

