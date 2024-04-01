import React from "react";

export interface LinkType {
  name: string;
  href: string;
  icon: string;
}

export interface ModalCustomProps {
  name: string;
  buttonString?: {
    cancel: string;
    add: string;
  };
  design?: string;
  open: boolean;
  onClose: () => void;
  onClickEvent: () => void;
  children: React.ReactNode;
}

export interface ModalProps {
  open: boolean;
  children: React.ReactNode;
}

export interface hoverProps {
  content: string;
  children: React.ReactNode;
}

export interface Recruit {
  career: [number, number];
  company: string;
  companyId: number;
  dueDate: [number, number, number];
  id: number;
  preferredRequirements: string[];
  qualificationRequirements: string[];
  source: string;
  thumbnailUrl: string;
  title: string;
  url: string;
}

export interface RecommendRecruit {
  recruitId: number;
  company: string;
  distance: number;
  url: string;
  dueDate: [number, number, number];
  intersection: string[];
  preferredRequirements: string[];
  qualificationRequirements: string[];
  title: string;
}

export interface Interview {
  interviewId: number;
  mainCategory: string;
  subCategory: string;
  question: string;
  example: string;
  answer: string;
}

export interface Essay {
  company: string;
  title: string;
  id: number;
  content: string;
}

export interface Title {
  id: number;
  title: string;
}
