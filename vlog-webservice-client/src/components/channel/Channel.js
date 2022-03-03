import React, { useState, useEffect } from "react";
import SideNav from "../common/SideNav";
import ChannelWrapperStyled from "../styled/channelStyled/ChannelWrapperStyled";
import ChannelContentWrapper from "../styled/channelStyled/ChannelContentWrapper";
import ChannelInfoWrapper from "./ChannelInfoWrapper";
import Content from "../main/Content";
import ContentWrapper from "../styled/mainStyled/ContentWrapper";
import { useMediaQuery } from "react-responsive";
import { useDispatch, useSelector } from "react-redux";
import Cookies from "universal-cookie";
import PostWrapperStyled from "../styled/mainStyled/PostWrapperStyled";
import Post from "../main/Post";
import { fetchChannel, fetchUserPost } from "../../redux/module/channel";
import { useParams } from "react-router-dom";

const Channel = () => {
  const { userName } = useParams();
  const { userId } = useParams();
  const dispatch = useDispatch();
  const channel = useSelector((state) => state.channel.channelData);
  const userPost = useSelector((state) => state.channel.userPost);
  const isMobile = useMediaQuery({
    query: "(max-width:1024px)",
  });

  document.body.style.overflow = "auto";

  useEffect(() => {
    dispatch(fetchChannel(userId));
    dispatch(fetchUserPost(userId));
  }, [userId]);

  return (
    <ChannelWrapperStyled>
      {isMobile ? null : <SideNav />}

      <ContentWrapper>
        <ChannelInfoWrapper />
        {
          <PostWrapperStyled>
            {userPost.map((post, i) => {
              return <Post post={post} key={i} />;
            })}
          </PostWrapperStyled>
        }
      </ContentWrapper>
    </ChannelWrapperStyled>
  );
};

export default Channel;
