"use client";
import { useState } from "react";
import { useMediaQuery } from "react-responsive";
import Image from "next/image";

interface FalseBtnProps {
  onNextClick: () => void;
}

export default function FalseBtn({ onNextClick }: FalseBtnProps) {
  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  const [hovered, setHovered] = useState(false);

  // 모바일이면 터치 이벤트 사용, 아니면 마우스 이벤트 사용
  const handleMouseEnter = (): void => {
    if (!isMobile) {
      setHovered(true);
    }
  };

  const handleMouseLeave = (): void => {
    if (!isMobile) {
      setHovered(false);
    }
  };

  const handleTouchStart = (): void => {
    if (isMobile) {
      setHovered(true);
    }
  };

  const handleTouchEnd = (): void => {
    if (isMobile) {
      setHovered(false);
    }
  };

  return (
    <div className="mx-10 my-5">
      <div
        className={`flex items-center justify-center cursor-pointer w-80 h-40 rounded-3xl drop-shadow-md transition-all ease-in duration-300 ${hovered ? "bg-f5red-100 scale-105" : "bg-f5gray-200"}`}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
        onTouchStart={handleTouchStart}
        onTouchEnd={handleTouchEnd}
        onClick={onNextClick}
      >
        <Image
          src={hovered ? "/images/falseRed.png" : "/images/falseGray.png"}
          alt={hovered ? "falseRed" : "falseGray"}
          width={120}
          height={120}
          className="transition-all duration-300 ease-in"
        />
      </div>
    </div>
  );
}