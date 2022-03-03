import React, { useEffect, useState } from "react";
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
import Cookies from "universal-cookie";
import EtcDropDown from "../styled/modalStyled/EtcDropDown";
import EtcButton from "../styled/modalStyled/EtcButton";
import EditButtons from "./EditButtons";

const SideContent = ({ isOpened, tags, src }) => {
  const cookies = new Cookies();
  const [isVisible, setIsVisible] = useState(false);
  const postDetail = useSelector((state) => state.post.postDetail);
  const navigate = useNavigate();
  const userCookie = cookies.get("user");
  const postsId = postDetail.postsId;
  const [picture, setPicture] = useState("");

  const tagList = tags.map((tag, index) => (
    <Tag key={index} tag={tag}>
      {tag}
    </Tag>
  ));

  const handleEtcClick = () => {
    setIsVisible(!isVisible);
  };

  const handleClick = (e) => {
    navigate(`/channel/${e.target.dataset.username}`);
  };

  const buttonRedner = () => {
    if (!userCookie) {
      return null;
    }
    if (userCookie.userId === postDetail.authorId) {
      return <EtcButton onClick={handleEtcClick}>...</EtcButton>;
    }
  };

  return (
    <SideWrapper>
      <div
        style={{
          display: "flex",
        }}
      >
        {isOpened && (
          <Link to={`/channel/${postDetail.authorName}/${postDetail.authorId}`}>
            <UserBlock src={src} userName={postDetail.authorName} />
          </Link>
        )}
        {buttonRedner()}
      </div>

      <EtcDropDown visible={isVisible}>
        <EditButtons target="post" postsId={postsId} />
      </EtcDropDown>

      <VideoDescription />
      <TagListWrapper>{tagList}</TagListWrapper>
      <ContentInfo />
      <CommentList />
      <CommentInput />
    </SideWrapper>
  );
};

export default SideContent;
