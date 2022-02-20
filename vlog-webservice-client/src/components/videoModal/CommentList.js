import React, { useEffect, useState } from "react";
import CommentListWrapper from "../styled/modalStyled/CommentListWrapper";
import Comment from "./Comment";
import { useSelector, useDispatch } from "react-redux";
import { fetchComments } from "../../redux/module/comment";

const CommentList = () => {
  const [isExist, setIsExist] = useState(false);
  const comments = useSelector((state) => state.comment.comments);

  const commentList = comments.map((comment, index) => (
    <Comment key={index} comment={comment}></Comment>
  ));

  return <CommentListWrapper>{commentList}</CommentListWrapper>;
};

export default CommentList;
