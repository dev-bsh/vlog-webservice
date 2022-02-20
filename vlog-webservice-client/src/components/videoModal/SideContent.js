import React, { useEffect } from "react";
import SideWrapper from "../styled/modalStyled/SideWrapper";
import UserBlcok from "../common/UserBlock";
import VideoDescription from "./VideoDescription";
import ContentInfo from "./ContentInfo";
import CommentList from "./CommentList";
import { Link } from "react-router-dom";
import CommentInput from "./CommentInput";
import UserBlock from "../common/UserBlock";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import UserBlockStlyed from "../styled/commonStyled/UserBlockStyled";
import { useSelector, useDispatch } from "react-redux";
import Tag from "../common/Tag";
import TagListWrapper from "../styled/commonStyled/TagListWrapper";
import { useNavigate } from "react-router-dom";
const SideContent = ({ isOpened, tags }) => {
  const postDetail = useSelector((state) => state.post.postDetail);
  const navigate = useNavigate();
  const tagList = tags.map((tag, index) => (
    <Tag key={index} tag={tag}>
      {tag}
    </Tag>
  ));

  const handleClick = (e) => {
    navigate(`/channel/${e.target.dataset.username}`);
  };
  return (
    <SideWrapper>
      <div>
        {isOpened && (
          <UserBlockStlyed onClick={handleClick}>
            <UserProfileStyled
              src=""
              width="32px"
              height="32px"
              data-username={postDetail.authorName}
            />
            <p>{postDetail.authorName}</p>
          </UserBlockStlyed>
        )}
      </div>
      <VideoDescription />
      <TagListWrapper>{tagList}</TagListWrapper>
      <ContentInfo />
      <CommentList />
      <CommentInput />
    </SideWrapper>
  );
};

export default SideContent;
