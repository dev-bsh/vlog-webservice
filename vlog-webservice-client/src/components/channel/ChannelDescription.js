import React, { useState } from "react";
import ChannelDescriptionStyled from "../styled/channelStyled/ChannelDescriptionStyled";
import { useSelector, useDispatch } from "react-redux";
import { getList } from "../../redux/module/channel";

const ChannelDescription = ({ setIsOpened }) => {
  const userPost = useSelector((state) => state.channel.userPost);
  const channel = useSelector((state) => state.channel.channelData);
  const dispatch = useDispatch();

  const handleClick = (e) => {
    dispatch(getList(e.target.dataset.target));
    setIsOpened((prev) => !prev);
  };

  return (
    <>
      <ChannelDescriptionStyled>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          Post : {userPost.length}
          <div
            onClick={handleClick}
            data-target="follower"
            style={{ cursor: "pointer" }}
          >
            Followers : {channel.followerNumber}
          </div>
          <div
            onClick={handleClick}
            data-target="following"
            style={{ cursor: "pointer" }}
          >
            Follow :{channel.followingNumber}
          </div>
        </div>
      </ChannelDescriptionStyled>
    </>
  );
};

export default ChannelDescription;
