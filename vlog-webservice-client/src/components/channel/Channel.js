import React from "react";
import SideNav from "../common/SideNav";
import ChannelWrapperStyled from "../styled/channelStyled/ChannelWrapperStyled";
import ChannelContentWrapper from "../styled/channelStyled/ChannelContentWrapper";
import ChannelInfoWrapper from "./ChannelInfoWrapper";
import Blank from "../styled/channelStyled/Blank";
import Content from "../main/Content";
import ContentWrapper from "../styled/mainStyled/ContentWrapper";
import { useParams } from "react-router-dom";

const Channel = () => {
  const { userName } = useParams();
  document.body.style.overflow = "auto";
  return (
    <ChannelWrapperStyled>
      <SideNav />
      <ContentWrapper>
        <h2>{userName}</h2>
        <ChannelInfoWrapper />
      </ContentWrapper>
    </ChannelWrapperStyled>
  );
};

export default Channel;
