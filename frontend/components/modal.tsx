"use client";
import React, { ReactNode, useEffect, useState } from "react";
import ReactDOM from "react-dom";

interface ModalProps {
  open: boolean;
  children: ReactNode;
}

export default function Modal({ open, children }: ModalProps) {
  const [prevScrollY, setPrevScrollY] = useState<number | undefined>(undefined);

  // 스크롤을 방지하고 현재 위치를 반환
  const preventScroll = () => {
    const currentScrollY = window.scrollY;
    document.body.style.position = "fixed";
    document.body.style.width = "100%";
    document.body.style.top = `-${currentScrollY}px`; // 현재 스크롤 위치
    document.body.style.overflowY = "scroll";
    setPrevScrollY(currentScrollY);
  };

  // 스크롤을 허용하고, 스크롤 방지 함수에서 반환된 위치로 이동
  const allowScroll = () => {
    document.body.style.position = "";
    document.body.style.width = "";
    document.body.style.top = "";
    document.body.style.overflowY = "";
    if (prevScrollY !== undefined) {
      window.scrollTo(0, prevScrollY);
    }
  };

  useEffect(() => {
    if (open) {
      preventScroll();
    } else {
      allowScroll();
    }
  }, [open]);

  if (!open) return null;

  return ReactDOM.createPortal(
    <>
      <div className="fixed top-0 left-0 right-0 bottom-0 bg-black bg-opacity-40 z-50" />
      <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 px-20 py-12 min-h-[570px] bg-white z-50 rounded-xl">
        {children}
      </div>
    </>,
    document.getElementById("globalModal") as HTMLElement
  );
}
