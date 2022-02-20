import React from "react";
import VideoDescriptionWrapper from "../styled/modalStyled/VideoDescriptionWrapper";
import { useSelector } from "react-redux";
const VideoDescription = () => {
  const postDetail = useSelector((state) => state.post.postDetail);

  return (
    <VideoDescriptionWrapper>{postDetail.description}</VideoDescriptionWrapper>
  );
};

export default VideoDescription;
