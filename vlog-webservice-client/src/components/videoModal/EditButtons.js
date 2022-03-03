import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { deletePost, fetchDetailPost } from "../../redux/module/post";
import {
  deleteComment,
  editComment,
  submitComment,
} from "../../redux/module/comment";

const EditButtons = ({ target, postsId, comment, handleClick }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [isEditing, setIsEditing] = useState(false);
  const [commentInput, setCommentInput] = useState("");

  const handleInput = (e) => {
    setCommentInput(e.target.value);
  };

  const handleDelete = () => {
    switch (target) {
      case "post":
        dispatch(deletePost(postsId));
        window.location.href = "/";
        return false;
      case "comment":
        dispatch(deleteComment(comment.commentId));
        handleClick();
    }
  };

  const handleEdit = () => {
    switch (target) {
      case "post":
        dispatch(fetchDetailPost(postsId));
        navigate("/edit");
      case "comment":
        setIsEditing(!isEditing);
    }
  };

  const handleSubmit = (e) => {
    if (commentInput !== "" && e.key === "Enter") {
      dispatch(
        editComment(postsId, comment.authorId, commentInput, comment.commentId)
      );
      setCommentInput("");
      e.target.value = "";
      handleClick();
      setIsEditing(false);
    }
  };

  return (
    <div style={{ display: "flex", flexDirection: "column" }}>
      <div
        style={{
          display: "flex",
        }}
      >
        <button
          onClick={handleEdit}
          style={{
            border: "none",
            color: "#2f3640",
            backgroundColor: "white",
          }}
        >
          {target} 수정
        </button>
        <button
          onClick={handleDelete}
          style={{
            border: "none",
            color: "#e84118",
            backgroundColor: "white",
          }}
        >
          {target} 삭제
        </button>
      </div>
      {isEditing && (
        <input
          type="text"
          placeholder="editing..."
          onChange={handleInput}
          onKeyPress={handleSubmit}
        />
      )}
    </div>
  );
};

export default EditButtons;
