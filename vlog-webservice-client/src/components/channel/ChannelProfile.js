import React from "react";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import Cookies from "universal-cookie";
import { useSelector } from "react-redux";

const ChannelProfile = () => {
  const channel = useSelector((state) => state.channel.channelData);

  return <UserProfileStyled src={channel.picture} />;
};

export default ChannelProfile;
