import React, { useState, useEffect } from "react";
import axios from "axios";
import UserBlock from "../common/UserBlock";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
const FollowList = ({ channel, setIsOpened }) => {
  const [users, setUsers] = useState([]);
  const target = useSelector((state) => state.channel.target);
  const handleClick = () => {
    setIsOpened((prev) => !prev);
  };
  useEffect(async () => {
    const response = await axios.get(
      `/api/v1/user/${channel.userId}/${target}`
    );

    setUsers(response.data);
  }, []);

  const userList = users.map((user, index) => (
    <Link
      to={`/channel/${user.name}/${user.userId}`}
      key={index}
      onClick={handleClick}
    >
      <div
        style={{
          margin: "1em 0",
        }}
      >
        <UserBlock src={user.picture} userName={user.name} email={user.email} />
      </div>
    </Link>
  ));

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "30em",
      }}
    >
      {userList}
    </div>
  );
};

export default FollowList;
