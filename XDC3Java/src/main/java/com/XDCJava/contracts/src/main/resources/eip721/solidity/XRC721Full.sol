pragma solidity ^0.5.0;

import "./XRC721.sol";
import "./XRC721Enumerable.sol";
import "./XRC721Metadata.sol";
/**
 * @title Full XRC721 Token
 * This implementation includes all the required and some optional functionality of the XRC721 standard
 * Moreover, it includes approve all functionality using operator terminology
 */



contract XRC721Full is XRC721, XRC721Enumerable, XRC721Metadata {
    constructor ()
    public XRC721Metadata("1", "test") {
        super._mint(msg.sender, 21);
    }
}


contract NftCreate
{
        XRC721Full[] public xrc;
     address[] public proposalList;
    
    function deploy() public
    {
        XRC721Full prop = new XRC721Full();
        xrc.push(prop);
    }
}