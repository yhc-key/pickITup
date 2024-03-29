"use client";
import React, { ReactNode } from "react";
import { useState } from "react";
import { hoverProps } from "@/type/interface";

export default function Tooltip({ content, children }: hoverProps) {
  // 호버 상태 저장
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div className="mb:hidden">
      {/* 마우스 이벤트에 따라 호버 상태 저장  */}
      <div
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        className="cursor-pointer"
      >
        {children}
      </div>
      <div className={`transition-opacity ease-in duration-300 ${isHovered ? 'opacity-100' : 'opacity-0'}`}>
        {isHovered ? (
          <div className="relative">
            <div className="absolute left-12 -top-11 px-5 py-2.5 whitespace-pre bg-f5gray-300 text-f5black-400 rounded-md text-xs">
            <div className="absolute w-4 h-4 -left-2 top-5 origin-center rotate-45 border-t-[1rem]  border-t-f5gray-300"></div>
              {content}
            </div>
          </div>
        ) : null}
        </div>
    </div>
  );
}
