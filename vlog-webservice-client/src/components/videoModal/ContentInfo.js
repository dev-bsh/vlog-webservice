import React from "react";
import ContentInfoWrapper from "../styled/modalStyled/ContentInfoWrapper";
import Like from "./Like";
import { MdSms } from "react-icons/md";
import { useSelector } from "react-redux";

const ContentInfo = () => {
  const postDetail = useSelector((state) => state.post.postDetail);

  return (
    <ContentInfoWrapper>
      <Like />
      Like: {postDetail.postsLike}
      <MdSms />
      Comments: {postDetail.totalCommentsSize}
      Views : {postDetail.views}
    </ContentInfoWrapper>
  );
};

export default ContentInfo;
