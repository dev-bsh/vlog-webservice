import { useEffect } from "react";
import { createPortal } from "react-dom";
import ModalStyled from "../styled/modalStyled/ModalStyled";
import OverlayStyled from "../styled/modalStyled/OverlayStyled";
import ModalWrapper from "../styled/modalStyled/ModalWrapper";
import VideoContentWrapper from "../styled/modalStyled/VideoContentWrapper";
import CloseButton from "../styled/modalStyled/CloseButton";
import { MdClose } from "react-icons/md";
import VideoContent from "../videoModal/VideoContent";
import SideContent from "../videoModal/SideContent";
import { useSelector, useDispatch } from "react-redux";

const VideoModal = ({ isOpened, children, onClose, videoSrc, tags, src }) => {
  if (!isOpened) {
    return null;
  }

  document.body.style.overflow = "hidden";
  const link = videoSrc.slice(0, -3);
  return createPortal(
    <ModalWrapper>
      <OverlayStyled onClick={onClose}>
        <CloseButton onClick={onClose}>
          <MdClose />
        </CloseButton>
      </OverlayStyled>

      <ModalStyled>
        <VideoContentWrapper>
          <video
            width="100%"
            height="100%"
            src={`http://localhost:8080/api/v1/posts/video/${link}mp4`}
            controls
            autoPlay
          />
        </VideoContentWrapper>
        <SideContent isOpened={isOpened} tags={tags} src={src} />
      </ModalStyled>
    </ModalWrapper>,
    document.getElementById("modal")
  );
};

export default VideoModal;
