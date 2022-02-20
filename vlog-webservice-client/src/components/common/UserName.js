import React from "react";
import { useSelector } from "react-redux";

const UserName = () => {
  const postDetail = useSelector((state) => state.post.postDetail);
  return <p>{postDetail.authorName}</p>;
};

export default UserName;
