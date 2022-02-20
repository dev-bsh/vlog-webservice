import React from "react";
import ChannelInfoStyled from "../styled/channelStyled/ChannelInfoStyled";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import ChannelDetaillWrapper from "../styled/channelStyled/ChannelDetaillWrapper";
import UserName from "../common/UserName";
import ChannelDescription from "../channel/ChannelDescription";

const ChannelInfoWrapper = () => {
  return (
    <ChannelInfoStyled>
      <UserProfileStyled src="" width="160px" height="160px" />
      <ChannelDetaillWrapper>
        <UserName />
        <ChannelDescription />
      </ChannelDetaillWrapper>
    </ChannelInfoStyled>
  );
};

export default ChannelInfoWrapper;
