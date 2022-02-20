import React, { memo, useState, useEffect } from "react";
import PostStyled from "../styled/mainStyled/PostStyled";
import UserBlock from "../common/UserBlock";
import VideoModal from "../videoModal/VideoModal";
import Modal from "react-modal";
import { useSelector, useDispatch } from "react-redux";
import { MdFavorite } from "react-icons/md";
import { MdPlayArrow } from "react-icons/md";
import { toggleModal } from "../../redux/module/post";
import ContentInfo from "../videoModal/ContentInfo";
import axios from "axios";
import { fetchDetailPost } from "../../redux/module/post";
import UserBlockStlyed from "../styled/commonStyled/UserBlockStyled";
import UserProfileStyled from "../styled/commonStyled/UserProfileStyled";
import ThumbnailWrapper from "../styled/mainStyled/ThumbnailWrapper";
import Tag from "../common/Tag";
import { fetchComments } from "../../redux/module/comment";
import { Link } from "react-router-dom";
import TagListWrapper from "../styled/commonStyled/TagListWrapper";

const Post = ({ post }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [postTag, setPostTag] = useState(post);
  const postDetail = useSelector((state) => state.post.postDetail);
  const dispatch = useDispatch();
  const isOpened = useSelector((state) => state.post.isOpened);

  const toggleModal = () => {
    const postId = post.postsId;
    setIsModalOpen(!isModalOpen);

    if (!isModalOpen) {
      dispatch(fetchDetailPost(post.postsId));
      dispatch(fetchComments(`${postId}`));
    }

    document.body.style.overflow = "auto";
  };

  const tagList = post.tags.map((tag, index) => (
    <Tag key={index} tag={tag}>
      {tag}
    </Tag>
  ));

  return (
    <>
      <PostStyled onClick={toggleModal}>
        <ThumbnailWrapper
          width="100%"
          height="80%"
          src={`http://localhost:8080/api/v1/posts/thumbnail/${post.thumbnailName}`}
        />
        <Link to={`/channel/${post.authorName}/`}>
          <UserBlockStlyed>
            <UserProfileStyled width="32px" height="32px" src="" />
            <p>{post.authorName}</p>
          </UserBlockStlyed>
        </Link>
        <MdFavorite /> :{post.postsLike}
        <MdPlayArrow /> : {post.views}
        <TagListWrapper>{tagList}</TagListWrapper>
      </PostStyled>

      <VideoModal
        isOpened={isModalOpen}
        onClose={toggleModal}
        videoSrc={post.thumbnailName}
        tags={post.tags}
      ></VideoModal>
    </>
  );
};

export default Post;
