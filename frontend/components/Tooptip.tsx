"use client";
import React, { ReactNode } from "react";
import { useState } from "react";


interface hoverProps {
  content: string;
  children: ReactNode;
}

export default function Tooltip({ content, children }: hoverProps) {
  // 호버 상태 저장
  const [isHovered, setIsHovered] = useState(false);

  return (
    <>
     {/* 마우스 이벤트에 따라 호버 상태 저장  */}
      <div
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        className="cursor-pointer"
      >
        {children}
      </div>
      <div>
        {/* 호버되면 content에 저장된 메세지를 보여주고, 아니면 보여주지 않음 */}
        {isHovered ? (
          <div className="relative">
            <div className="absolute left-1 -top-8 px-5 py-2.5 whitespace-pre bg-f5gray-300 text-f5black-400 rounded-md transition-all text-xs">
              {content}
            </div>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </>
  );
}
