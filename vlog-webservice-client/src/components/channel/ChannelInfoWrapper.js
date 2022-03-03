import React, { useState, useEffect } from "react";
import ChannelInfoStyled from "../styled/channelStyled/ChannelInfoStyled";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import ChannelDetaillWrapper from "../styled/channelStyled/ChannelDetaillWrapper";
import UserName from "../common/UserName";
import ChannelProfile from "./ChannelProfile";
import ChannelDescription from "../channel/ChannelDescription";
import Cookies from "universal-cookie";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import FollowButton from "../styled/commonStyled/FollowButton";
import FollowList from "./FollowList";

import {
  setFollow,
  getFollowing,
  setUnfollow,
} from "../../redux/module/follow";
import axios from "axios";

const ChannelInfoWrapper = () => {
  const [isOpened, setIsOpened] = useState(false);
  const channel = useSelector((state) => state.channel.channelData);
  const [isFollowed, setIsFollowed] = useState(false);
  const cookies = new Cookies();
  const dispatch = useDispatch();

  const handleFollow = () => {
    const userId = cookies.get("user").userId;
    dispatch(getFollowing(userId));
    dispatch(setFollow(userId, channel.userId));
    setIsFollowed(true);
  };

  const handleUnFollow = () => {
    const userId = cookies.get("user").userId;
    dispatch(setUnfollow(userId, channel.userId));
    setIsFollowed(false);
  };

  const compareFollow = async () => {
    if (cookies.get("isLoggedIn")) {
      const response = await axios.get(
        `/api/v1/user/${cookies.get("user").userId}/following`
      );
      response.data.forEach((element) => {
        if (element.userId === cookies.get("user").userId) {
          setIsFollowed(true);
          return;
        }
      });
    }
  };

  useEffect(() => {
    compareFollow();
  }, []);

  return (
    <>
      <ChannelInfoStyled>
        <ChannelProfile />
        <ChannelDetaillWrapper>
          <h2>{channel.name}</h2>
          <ChannelDescription setIsOpened={setIsOpened} />
        </ChannelDetaillWrapper>
        {isFollowed ? (
          <FollowButton onClick={handleUnFollow}>Unfollow</FollowButton>
        ) : (
          <FollowButton onClick={handleFollow}>Follow</FollowButton>
        )}
      </ChannelInfoStyled>
      {isOpened && (
        <FollowList
          visible={isOpened}
          channel={channel}
          setIsOpened={setIsOpened}
        />
      )}
    </>
  );
};

export default ChannelInfoWrapper;
