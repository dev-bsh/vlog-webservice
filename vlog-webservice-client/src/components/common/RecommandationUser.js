import React, { useState, useEffect } from "react";
import RecommandationContentStyled from "../styled/commonStyled/RecommandationContentStyled";
import UserBlock from "./UserBlock";
import { Link } from "react-router-dom";
import RecommandTextWrapper from "../styled/commonStyled/RecommandTextWrapper";
import { useSelector, useDispatch } from "react-redux";
import { fetchRecommendUsers } from "../../redux/module/recommend";
import axios from "axios";
const RecommandationUser = () => {
  const dispatch = useDispatch();
  const [recommendUsers, setRecommendUsers] = useState([]);

  useEffect(async () => {
    const response = await axios.get(
      `http://localhost:8080/api/v1/recommend/user`
    );
    setRecommendUsers(response.data);
  }, []);

  const userList = () =>
    recommendUsers.map((user, index) => (
      <Link to={`/channel/${user.name}/${user.userId}`} key={index}>
        <UserBlock key={index} userName={user.name} src={user.picture} />
      </Link>
    ));

  return (
    <>
      <RecommandTextWrapper>Recommnad User</RecommandTextWrapper>
      <RecommandationContentStyled>{userList()}</RecommandationContentStyled>
    </>
  );
};

export default RecommandationUser;
